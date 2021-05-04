package com.miguel_santos.notinstagram.main.camera.presentation;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;

import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.view.AbstractActivity;
import com.miguel_santos.notinstagram.main.camera.datasource.AddLocalDataSource;

import butterknife.BindView;

public class AddCaptionActivity extends AbstractActivity implements AddCaptionView {

    private Uri uri;
    private AddPresenter presenter;

    @BindView(R.id.main_add_caption_image)
    ImageView imageCaption;
    @BindView(R.id.main_add_caption_edt)
    EditText edtCaption;


    public static void launch(Context context, Uri uri) {
        Intent intent = new Intent(context, AddCaptionActivity.class);
        intent.putExtra("uri", uri);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = findViewById(R.id.add_toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            Drawable drawable = findDrawable(R.drawable.ic_arrow_back);
            getSupportActionBar().setHomeAsUpIndicator(drawable);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    protected void onInject() {
        uri = getIntent().getExtras().getParcelable("uri");
        imageCaption.setImageURI(uri);

        AddLocalDataSource dataSource = new AddLocalDataSource();
        presenter = new AddPresenter(this, dataSource);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_share:
                presenter.createPost(uri, edtCaption.getText().toString());
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_share, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void postSaved() {
        // TODO: 03/05/2021
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_add_caption;
    }
}