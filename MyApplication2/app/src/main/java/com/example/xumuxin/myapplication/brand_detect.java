package com.example.xumuxin.myapplication;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yalantis.ucrop.UCrop;

import org.xutils.common.Callback;
import org.xutils.common.util.LogUtil;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_FIRST_USER;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.FINGERPRINT_SERVICE;
import static com.example.xumuxin.myapplication.MainActivity.SELECT_PHOTO;
import static com.example.xumuxin.myapplication.MainActivity.TAKE_PHOTO;
import static com.example.xumuxin.myapplication.MainActivity.tag;

/**
 * Created by XDDN2 on 2018/5/7.
 */
@ContentView(R.layout.brand_detect)
public class brand_detect extends Fragment {
    @ViewInject(R.id.hot_brands)
    private RecyclerView recyclerView;
    @ViewInject(R.id.left_tag)
    private View left_tag;
    @ViewInject(R.id.right_tag)
    private View right_tag;
    @ViewInject(R.id.display_logo)
    private ImageView img;
    private String fake_url, logo_url;
    private List<brandInfo> datas = new ArrayList<>();
    private brand_adapter adapter;
    private int currentStatus;
    private Uri imageUri;
    private String ip = "http://ras.sysu.edu.cn:19232/";

    private Brand_search_items brand_search_items;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        try {
            view = x.view().inject(this, inflater, container);

        } catch (Exception e) {
            Log.e("brand_detect", "wrong!", e);
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            initDatas();
            initViews(view);
        } catch (Exception e) {
            Log.e("detect", "wrong!", e);
        }
    }

    private void initDatas() {
        datas.add(new brandInfo("ss", "acura"));
        datas.add(new brandInfo("ss", "audi"));
        datas.add(new brandInfo("ss", "benz"));
        datas.add(new brandInfo("ss", "karas"));
        askForHotSearchLogo();
    }

    void initViews(View view) {
        currentStatus = 1;
        fake_url = "file:///res//drawable//acura_0024.jpg";
        logo_url = "file:///res//drawable//acura_0024.jpg";
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new brand_adapter(getContext(), datas);
        adapter.setOnItemClickLitener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                clickItem(parent, view, position, id);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Event(value = {R.id.logo_detect, R.id.fake_detect}, type = View.OnClickListener.class)
    private void getEvent(View view) {

        switch (view.getId()) {
            case R.id.logo_detect:
                switchToRightPos(1);
                break;
            case R.id.fake_detect:
                switchToRightPos(2);
                break;
            default:
                break;
        }
    }

    private void switchToRightPos(int number) {
        try {
            if (currentStatus == number)
                return;
            switch (number) {
                case 1:
                    left_tag.setVisibility(View.VISIBLE);
                    right_tag.setVisibility(View.INVISIBLE);
                    if (logo_url != "") {
                        x.image().bind(img, logo_url);
                        Log.e("logo_url", logo_url);
                    }
                    break;
                case 2:
                    left_tag.setVisibility(View.INVISIBLE);
                    right_tag.setVisibility(View.VISIBLE);
                    if (fake_url != "") {
                        x.image().bind(img, fake_url);
                        Log.e("fake_url", fake_url);
                    }
                    break;
                default:
                    break;
            }
            currentStatus = number;
            Log.e("switch", "start " + currentStatus);
        } catch (Exception e) {
            Log.e("switch", "wrong!", e);
        }

    }



    @Event(value = R.id.take_photo, type = View.OnClickListener.class)
    private void takePhoto(View view) {
        String filename;
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        filename = format.format(date);
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        File outputImage = new File(path,filename+".jpg");
        Log.e("output", outputImage.getAbsolutePath());
        try {
            if (outputImage.exists()) {
                outputImage.delete();
            }
            outputImage.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(getContext(),
                    "com.example.xumuxin.myapplication.fileprovider", outputImage);
        } else {
            imageUri = Uri.fromFile(outputImage);
        }
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, TAKE_PHOTO);
    }

    @Event(value = R.id.select_photo, type = View.OnClickListener.class)
    private void selectPhoto(View view) {
        if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE }, 1);
        } else {
            openAlbum();
        }
    }

    public void openAlbum() {
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,SELECT_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch(requestCode) {
            case 1:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(getContext(),"You denied the permission",Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    public void startCropActivity(Uri uri) {
        String fileName = "myCroppedImage" +  currentStatus + ".jpg";
        File f = new File(getContext().getCacheDir(), fileName);

        try {
            if (f.exists()) {
                x.image().clearMemCache();
                Log.e("delete", "success " + f.delete() + f.exists());

                f = new File(getContext().getCacheDir(), fileName);
            }
        } catch (Exception e) {
            Log.e("create file", "Wrong!", e);
        }
        UCrop.of(uri, Uri.fromFile(f))
                .withAspectRatio(1, 1)
                .withMaxResultSize(512, 512)
                .start(getContext(), this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("activity result", "reqCode: " + requestCode);
        switch (requestCode) {
            case TAKE_PHOTO:
                Log.e("take photo", "begin");
                if (resultCode == RESULT_OK) {
                    try {
                        startCropActivity(imageUri);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case SELECT_PHOTO:
                Log.e("select photo", "begin");
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        handleImageOnKitKat(data);
                    } else {
                        handleImageBeforeKitKat(data);
                    }
                }
                break;

            default:

                Log.e("crop photo", "begin " + resultCode);
                if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
                    Log.e("crop", "success");
                    Uri resultUri = UCrop.getOutput(data);

                    String imagePath = resultUri.toString();
                    if (currentStatus == 1) {
                        logo_url = imagePath;
                    } else {
                        fake_url = imagePath;
                    }
                    try {
                        Log.e("imagePath", resultUri.getPath()+" "+resultUri.toString());

                        x.image().bind(img, resultUri.toString());
                        uploadimage(imagePath);
//                        uploadImage(imagePath);
                    } catch (Exception e) {
                        LogUtil.e(e.getMessage(), e);
                        Log.e("bind image", "Wrong!", e);

                    }


                }
                break;
        }
    }


    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        imageUri = data.getData();
        startCropActivity(imageUri);
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        imageUri = uri;
        startCropActivity(imageUri);
    }

    private void clickItem(AdapterView<?> parent, View view, int position, long id) {
        try {
            Log.e("item click", ""+position);
            String brand_name = adapter.getItem(position).getName();
            Intent intent = new Intent(getActivity(), brand_details.class);
            intent.putExtra("brand_name", brand_name);
            getActivity().startActivity(intent);

        } catch (Exception e) {
            Log.e("start_activity", "Wrong", e);
        }

    }

    private void askForHotSearchLogo() {
        String url = "http://private-anon-12c211a2e0-logorecognition.apiary-mock.com/brand_heat/?num=4";
        RequestParams requestParams = new RequestParams(url);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                brand_search_items = gson.fromJson(result, Brand_search_items.class);
                Log.e("get index", "Content: " + brand_search_items.getBrand_names().get(0)+"...");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(x.app(), ex.getMessage(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void uploadimage(String url) {
        String final_url = ip + "images/";

        String fileName = "myCroppedImage" +  currentStatus + ".jpg";
        File f = new File(getContext().getCacheDir(), fileName);
        RequestParams requestParams = new RequestParams(final_url);
        requestParams.setMultipart(true);

        requestParams.addBodyParameter("image_file", f, "image/jpeg");
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                upload_feedback_infor i = gson.fromJson(result, upload_feedback_infor.class);
                fake_url = ip+"images/?image_path="+i.getImage_path();
                Log.e("get index", "Content: " + i.getImage_path() + " " + i.getMessage());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.e("upload error", ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Toast.makeText(x.app(), "cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFinished() {

            }
        });
    }



    private void uploadImage(String imagePath)
    {

        new NetworkTask().execute(imagePath);
    }

    /**
     * 访问网络AsyncTask,访问网络在子线程进行并返回主线程通知访问的结果
     */
    class NetworkTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            return doPost(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            if(!"error".equals(result)) {}
        }
    }

    private String doPost(String imagePath) {
        OkHttpClient mOkHttpClient = new OkHttpClient.Builder().connectTimeout(15, TimeUnit.SECONDS).readTimeout(20, TimeUnit.SECONDS)
                .build();

        String result = "error";
        MultipartBody.Builder builder = new MultipartBody.Builder();
        String fileName = "myCroppedImage" +  currentStatus + ".jpg";
        File f = new File(getContext().getCacheDir(), fileName);
        imagePath = f.getPath();
        // 这里演示添加用户ID
        // builder.addFormDataPart("param1", "20160519142605");
        builder.addFormDataPart("img", f.getPath(),
                RequestBody.create(MediaType.parse("image/jpeg"), f));

        RequestBody requestBody = builder.build();
        Request.Builder reqBuilder = new Request.Builder();
        String url = ip+"images0";
        Request request = reqBuilder
                // + "/Upload_html"
                .url(url)
                .post(requestBody)
                .build();

        Log.d(tag, "请求地址 " + ip+"images");
        try{

            Response response = mOkHttpClient.newCall(request).execute();
            Log.e(tag, "响应码 " + response.code());
            if (response.isSuccessful()) {
//                String resultValue = response.body().string();
//                Message temp = new Message();
//                temp.what = 1;
//                Bundle bundle = new Bundle();
//
//                bundle.putString("res", resultValue);
//                temp.setData(bundle);
//                myHandler.sendMessage(temp);
//                Log.e(tag, "响应体 " + resultValue);
//                return resultValue;
            }
        } catch (Exception e) {
            System.out.println("上传失败:e.getLocalizedMessage() = " + e.getLocalizedMessage());
            e.printStackTrace();
        }
        return result;
    }

}
