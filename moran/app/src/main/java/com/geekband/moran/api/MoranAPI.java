package com.geekband.moran.api;

import android.content.Context;
import android.graphics.BitmapFactory;



public class MoranAPI {
    private static Context mContext;






//    /**
//     * 获取病例接口
//     *
//     * @param auth
//     * @param year
//     * @param responseHandler
//     */
//    public static void getDesPhoto(String auth, String year,
//                                   AsyncHttpResponseHandler<?> responseHandler) {
//        AsyncHttpClient client = HttpManager.getHttpClientInstance(mContext);
//        RequestParams params = HttpManager.createBaseRequestParams();
//        params.put("auth", auth);
//        params.put("year", year);
//        client.post(HttpManager.mTestYishengApi + HttpManager.mGetCase, params,
//                responseHandler);
//    }

//    /**
//     * 获取病例接口
//     *
//     * @param auth
//     * @param year
//     * @param responseHandler
//     */
//    public static void getDesPhoto(String patientid, String auth, String year,
//                                   AsyncHttpResponseHandler<?> responseHandler) {
//        AsyncHttpClient client = HttpManager.getHttpClientInstance(mContext);
//        RequestParams params = HttpManager.createBaseRequestParams();
//        params.put("userid", patientid);
//        params.put("auth", auth);
//        params.put("year", year);
//        client.post(HttpManager.mTestYishengApi
//                + HttpManager.mGetCase, params, responseHandler);
//    }

//    /**
//     * 上传病例接口
//     *
//     * @param auth
//     * @param image1
//     * @param name
//     * @param responseHandler
//     */
//    public static void uploadDesPhoto(String auth, String image1, String name,
//                                      AsyncHttpResponseHandler<?> responseHandler) {
//        AsyncHttpClient client = HttpManager.getHttpClientInstance(mContext);
//        RequestParams params = HttpManager.createBaseRequestParams();
//        try {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            ByteArrayOutputStream thumbbaos = new ByteArrayOutputStream();
//
//            // 将bitmap一字节流输出 Bitmap.CompressFormat.PNG 压缩格式，100：压缩率，baos：字节流
//            // BitmapFactory.decodeFile(image1).compress(
//            // Bitmap.CompressFormat.PNG, 100, baos);
//            BitmapFactory.Options o2 = new BitmapFactory.Options();
//            o2.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(image1, o2);
//            o2.inSampleSize = computeSampleSize(o2, -1, 1024 * 800);
//            o2.inJustDecodeBounds = false;
//            o2.inPurgeable = true;
//            o2.inInputShareable = true;
//            o2.inDither = false;
//            o2.inPurgeable = true;
//            o2.inTempStorage = new byte[16 * 1024];
//            Bitmap bitmap = BitmapFactory.decodeFile(image1, o2);
//            Bitmap thumbBitmap = BitmapFactory.decodeFile(image1, o2);
//            int size = bitmap.getByteCount();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//            thumbBitmap.compress(Bitmap.CompressFormat.JPEG, 60, thumbbaos);
//
//            byte[] buffer = baos.toByteArray();
//            byte[] thumbbuffer = thumbbaos.toByteArray();
//            baos.close();
//            thumbbaos.close();
//            String photo = Base64.encodeToString(buffer, 0, buffer.length,
//                    Base64.DEFAULT);
//            String thumbPhoto = Base64.encodeToString(thumbbuffer, 0, thumbbuffer.length,
//                    Base64.DEFAULT);
//            // 将图片的字节流数据加密成base64字符输出
////            Map<String, String> array = new HashMap<String, String>();
////            array.put("data", photo);
////            array.put("filename", "测试图片");
////            array.put("filesize", "111111");
////            array.put("exit", "jpg");
//
////            List<Map<String, String>> files = new ArrayList<Map<String, String>>();
////            files.add(array);
//
//            params.put("auth", auth);
//            params.put("filename", name);
//            params.put("filesize", String.valueOf(size));
//            params.put("fileext", "jpg");
//            params.put("data", photo);
//            params.put("small", thumbPhoto);
//            //params.put("files", JSON.toJSONString(files));
//
//            client.post(HttpManager.mTestYishengApi + HttpManager.mUploadCase,
//                    params, responseHandler);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

//    /**
//     * 上传头像接口
//     *
//     * @param auth
//     * @param image1
//     * @param responseHandler
//     */
//    public static void uploadHeadImage(String auth, String image1,
//                                       String image2, String image3, String image4,
//                                       AsyncHttpResponseHandler<?> responseHandler) {
//        AsyncHttpClient client = HttpManager.getHttpClientInstance(mContext);
//        RequestParams params = HttpManager.createBaseRequestParams();
//        try {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//            // 将bitmap一字节流输出 Bitmap.CompressFormat.PNG 压缩格式，100：压缩率，baos：字节流
//            // BitmapFactory.decodeFile(image1).compress(
//            // Bitmap.CompressFormat.PNG, 100, baos);
//            BitmapFactory.Options o2 = new BitmapFactory.Options();
//            o2.inJustDecodeBounds = true;
//            BitmapFactory.decodeFile(image1, o2);
//            o2.inSampleSize = computeSampleSize(o2, -1, 1024 * 800);
//            o2.inJustDecodeBounds = false;
//            o2.inPurgeable = true;
//            o2.inInputShareable = true;
//            o2.inDither = false;
//            o2.inPurgeable = true;
//            o2.inTempStorage = new byte[16 * 1024];
//            Bitmap bitmap = BitmapFactory.decodeFile(image1, o2);
//            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//            bitmap.recycle();
//            byte[] buffer = baos.toByteArray();
//            baos.close();
//
//            // 将图片的字节流数据加密成base64字符输出
//            String photo = Base64.encodeToString(buffer, 0, buffer.length,
//                    Base64.DEFAULT);
//            params.put("auth", auth);
//            params.put("image180", photo);
//            params.put("image90", photo);
//            params.put("image45", photo);
//            params.put("image30", photo);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        client.post(HttpManager.mTestYishengApi + HttpManager.mUploadHead,
//                params, responseHandler);
//    }

    public static int computeSampleSize(BitmapFactory.Options options,
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

    private static int computeInitialSampleSize(BitmapFactory.Options options,
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

//    /**
//     * 登录接口
//     *
//     * @param loginName
//     * @param password
//     * @param responseHandler
//     */
//    public static void login(String loginName, String password,
//                             AsyncHttpResponseHandler responseHandler) {
//        AsyncHttpClient client = HttpManager.getHttpClientInstance(mContext);
//        RequestParams params = HttpManager.createBaseRequestParams();
//        params.put("username", loginName);
//        params.put("password", password);
//        client.post(HttpManager.mGeekBandApi + HttpManager.mLogin, params,
//                responseHandler);
//    }


    /**
     * 注册接口
     * @param username  用户名
     * @param email 邮箱
     * @param password  密码
     * @param responseHandler
     */
//    public static void register(String username , String email, String password,
//                             AsyncHttpResponseHandler responseHandler) {
//        AsyncHttpClient client = HttpManager.getHttpClientInstance(mContext);
//        RequestParams params = HttpManager.createBaseRequestParams();
//        params.put("username", username);
//        params.put("password", password);
//        params.put("email" , email);
//        params.put("gbid" , gid);
//        client.post(HttpManager.mGeekBandApi + HttpManager.mRegister, params,
//                responseHandler);
//    }






}
