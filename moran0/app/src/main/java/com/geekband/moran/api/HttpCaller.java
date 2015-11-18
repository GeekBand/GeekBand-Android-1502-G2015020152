package com.geekband.moran.api;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import com.geekband.moran.R;
import com.geekband.moran.libcore.io.DiskLruCache;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class HttpCaller {
    private Context appcontext = null;
    private View view;
    private Set<BitmapWorkerTask> taskCollection;
    private DiskLruCache mDiskLruCache;
    private BitmapFactory.Options o2;
    public static HashMap<String, Bitmap> list = new HashMap<String, Bitmap>();

    @SuppressLint("NewApi")
    public HttpCaller(Context appcontext) {
        super();
        this.appcontext = appcontext;
        taskCollection = new HashSet<BitmapWorkerTask>();
        try {
            File cacheDir = getDiskCacheDir(appcontext, "thumb");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            mDiskLruCache = DiskLruCache.open(cacheDir,
                    getAppVersion(appcontext), 1, 10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DiskLruCache getmDiskLruCache() {
        return mDiskLruCache;
    }

    public Context getAppcontext() {
        return appcontext;
    }

    public void setAppcontext(Context appcontext) {
        this.appcontext = appcontext;
    }

    public void loadBitmaps(ImageView imageView, String imageUrl, View v,
                            String tag, String isRound, String isMember, String balance) {
        try {
            view = v;
            BitmapWorkerTask task = new BitmapWorkerTask();
            taskCollection.add(task);
            String[] params = {imageUrl, tag, isRound, isMember, balance};
            task.execute(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void cancelAllTasks() {
        if (taskCollection != null) {
            for (BitmapWorkerTask task : taskCollection) {
                task.cancel(true);
            }
        }
    }

    public Bitmap getBitmapFromDiskCache(String key) {
        try {
            DiskLruCache.Snapshot snapShot = mDiskLruCache.get(key);
            if (snapShot != null) {
                InputStream is = snapShot.getInputStream(0);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @SuppressLint("NewApi")
    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {
            if (context.getExternalCacheDir() != null)
                cachePath = context.getExternalCacheDir().getPath();
            else
                cachePath = context.getCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }

    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return info.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }

    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }

    public void fluchCache() {
        if (mDiskLruCache != null) {
            try {
                mDiskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

        private String imageUrl;
        private String tag;
        private String isRound;
        private String isMember;
        private String balance;
        Bitmap bitmap = null;
        Bitmap newbm = null;
        FileDescriptor fileDescriptor = null;
        FileInputStream fileInputStream = null;
        DiskLruCache.Snapshot snapShot = null;
        boolean isFailImage = false;

        @Override
        protected Bitmap doInBackground(String... params) {
            imageUrl = params[0];
            tag = params[1];
            isRound = params[2];
            isMember = params[3];
            balance = params[4];
            if (isCancelled()) {
                return null;
            }

            if (list.containsKey(imageUrl)) {
                newbm = list.get(imageUrl);
//                if (balance != null && balance.equals("1")) {
//                    Canvas canvas = new Canvas(newbm);
//                    Paint paint = new Paint();
//                    ColorMatrix colorMatrix = new ColorMatrix();
//                    colorMatrix.setSaturation(0);
//                    ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
//                            colorMatrix);
//                    paint.setColorFilter(colorMatrixFilter);
//                    canvas.drawBitmap(newbm, 0, 0, paint);
//                }
                return newbm;
            } else {
                try {
                    String key = "";
                    if (tag != null && !tag.equals(""))
                        key = hashKeyForDisk(tag.substring(0,
                                tag.lastIndexOf("/")));
                    else
                        key = hashKeyForDisk(imageUrl);

                    snapShot = mDiskLruCache.get(key);

                    if (snapShot == null) {
                        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
                        if (editor != null) {
                            OutputStream outputStream = editor
                                    .newOutputStream(0);
                            if (imageUrl.startsWith("file://")) {
                                if (downloadUrlToStreamFromFile(
                                        imageUrl.substring(7), outputStream)) {
                                    editor.commit();
                                } else {
                                    editor.abort();
                                }
                            } else {
                                if (downloadUrlToStream(imageUrl, outputStream)) {
                                    editor.commit();
                                } else {
                                    editor.abort();
                                }
                            }
                        }
                        snapShot = mDiskLruCache.get(key);
                    }

                    if (snapShot != null) {
                        fileInputStream = (FileInputStream) snapShot
                                .getInputStream(0);
                        fileDescriptor = fileInputStream.getFD();
                    }

                    if (fileDescriptor != null) {
                        o2 = new BitmapFactory.Options();
                        o2.inJustDecodeBounds = true;
                        BitmapFactory.decodeFileDescriptor(fileDescriptor,
                                null, o2);
                        o2.inSampleSize = computeSampleSize(o2, -1, 1024 * 800);
                        o2.inJustDecodeBounds = false;
                        o2.inInputShareable = true;
                        o2.inPurgeable = true;
                        o2.inTempStorage = new byte[16 * 1024];
                        bitmap = BitmapFactory.decodeFileDescriptor(
                                fileDescriptor, null, o2);
                    }

                    if (bitmap == null) {
                        isFailImage = true;
                        bitmap = BitmapFactory
                                .decodeResource(appcontext.getResources(),
                                        R.mipmap.fail_image_icon).copy(Config.ARGB_8888, true);
                    }

                    // 获得图片的宽高
                    int width = bitmap.getWidth();
                    int height = bitmap.getHeight();

                    // 设置想要的大小
                    int newWidth = 0;
                    int newHeight = 0;

                    if (width > height) {
                        newWidth = appcontext.getResources()
                                .getDimensionPixelSize(R.dimen.maxWidth);
                        newHeight = (height * newWidth) / width;
                    } else if (width < height) {
                        newWidth = appcontext.getResources()
                                .getDimensionPixelSize(R.dimen.minWidth);
                        newHeight = (height * newWidth) / width;
                    } else if (width == height) {
                        newWidth = appcontext.getResources()
                                .getDimensionPixelSize(R.dimen.minWidth);
                        newHeight = newWidth;
                    }

                    // 计算缩放比例
                    float scaleWidth = ((float) newWidth) / width;
                    float scaleHeight = ((float) newHeight) / height;

                    // 取得想要缩放的matrix参数
                    Matrix matrix = new Matrix();
                    matrix.postScale(scaleWidth, scaleHeight);

                    // 得到新的图片
                    if (isMember != null && isMember.equals("1")) {
                        if (!isFailImage)
                            list.put(imageUrl, bitmap);
                        return bitmap;
                    } else {
                        newbm = Bitmap.createBitmap(bitmap, 0, 0, width,
                                height, matrix, true);
                        if (balance != null && balance.equals("1")) {
                            Canvas canvas = new Canvas(newbm);
                            Paint paint = new Paint();
                            ColorMatrix colorMatrix = new ColorMatrix();
                            colorMatrix.setSaturation(0);
                            ColorMatrixColorFilter colorMatrixFilter = new ColorMatrixColorFilter(
                                    colorMatrix);
                            paint.setColorFilter(colorMatrixFilter);
                            canvas.drawBitmap(newbm, 0, 0, paint);
                        }
                        if (!isFailImage)
                            list.put(imageUrl, newbm);
                        return newbm;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fileDescriptor == null && fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e) {
                        }
                    }
                    if (isMember != null && isMember.equals("1")) {

                    } else {
                        if (bitmap != null && !bitmap.isRecycled()) {
                            bitmap.recycle();
                            bitmap = null;
                        }
                        System.gc();
                    }
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            try {
                ImageView imageView = null;
                if (view.getTag() != null && !view.getTag().equals("")
                        && view.getTag().equals("settings_head_view"))
                    imageView = (ImageView) view;
                else if (view.getTag() != null && !view.getTag().equals("")
                        && view.getTag().equals("map_head_first"))
                    imageView = (ImageView) view;
                else if (view.getTag() != null && !view.getTag().equals("")
                        && view.getTag().equals("map_head_second"))
                    imageView = (ImageView) view;
                else if (view.getTag() != null && !view.getTag().equals("")
                        && view.getTag().equals("map_head_third"))
                    imageView = (ImageView) view;
                else if (view.getTag() != null && !view.getTag().equals("")
                        && view.getTag().equals("map_head_four"))
                    imageView = (ImageView) view;
                else if (view.getTag() != null && !view.getTag().equals("")
                        && view.getTag().equals("map_head_fifty"))
                    imageView = (ImageView) view;
                else {
                    if (tag != null && !tag.equals(""))
                        imageView = (ImageView) view.findViewWithTag(tag);
                    else
                        imageView = (ImageView) view.findViewWithTag(imageUrl);
                }
                if (imageView != null && bitmap != null) {
                    if (isRound.equals("1")) {
                        imageView.setImageBitmap(toRoundCorner(bitmap, 40));
                    } else {
                        imageView.setImageBitmap(bitmap);
                    }
                    //imageView.setImageBitmap(bitmap);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            taskCollection.remove(this);
        }
    }

    public boolean downloadUrlToStreamFromFile(String urlString,
                                               OutputStream outputStream) {
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            File file = new File(urlString);
            FileInputStream fs = new FileInputStream(file);
            in = new BufferedInputStream(fs, 8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean downloadUrlToStream(String urlString,
                                       OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;
        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(),
                    8 * 1024);
            out = new BufferedOutputStream(outputStream, 8 * 1024);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public int computeSampleSize(BitmapFactory.Options options,
                                 int minSideLength, int maxNumOfPixels) {
        int initialSize = computeInitialSampleSize(options, minSideLength,
                maxNumOfPixels);
        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8 * 8;
        }
        return roundedSize;
    }

    private int computeInitialSampleSize(BitmapFactory.Options options,
                                         int minSideLength, int maxNumOfPixels) {
        double w = options.outWidth;
        double h = options.outHeight;
        int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
                .sqrt(w * h / maxNumOfPixels));
        int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
                Math.floor(w / minSideLength), Math.floor(h / minSideLength));
        if (upperBound < lowerBound) {
            // return the larger one when there is no overlapping zone.
            return lowerBound;
        }

        if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
            return 1;
        } else if (minSideLength == -1) {
            return lowerBound;
        } else {
            return upperBound;
        }
    }

    /**
     * 获取圆角位图的方法
     *
     * @param bitmap 需要转化成圆角的位图
     * @param pixels 圆角的度数，数值越大，圆角越大
     * @return 处理后的圆角位图
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        int size = bitmap.getHeight() >= bitmap.getWidth() ? bitmap.getWidth()
                : bitmap.getHeight();
        Bitmap output = Bitmap.createBitmap(size, size, Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, size, size);
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
}
