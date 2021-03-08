package com.miguel_santos.notinstagram.register.presentation;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.miguel_santos.notinstagram.R;
import com.miguel_santos.notinstagram.common.view.AbstractFragment;
import com.miguel_santos.notinstagram.common.view.LoadingButton;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterPhotoFragment extends AbstractFragment<RegisterPresenter> implements RegisterView.PhotoView {

    @BindView(R.id.register_btn_next)
    LoadingButton btnNext;

    public RegisterPhotoFragment() {

    }

    public static RegisterPhotoFragment newInstance(RegisterPresenter presenter) {
        RegisterPhotoFragment fragment = new RegisterPhotoFragment();
        fragment.setPresenter(presenter);
        return fragment;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnNext.setEnabled(true);

        /*
        CustomDialog customDialog = new CustomDialog.Builder(getContext())
                .setTitle(R.string.define_photo_profile)
                .addButton((v) -> {
                    switch (v.getId()) {
                        case R.string.take_picture:
                            Log.i("TESTE", "Taking picture");
                            break;
                        case R.string.search_gallery:
                            Log.i("TESTE", "Procurando a galerinha");
                    }
                }, R.string.import_from_facebook,R.string.take_picture, R.string.search_gallery)
                .build();
        customDialog.show();
        */
    }

    @OnClick(R.id.register_btn_next)
    public void onButtonNextClick() {
        // TODO: 08/03/2021
    }

    @OnClick(R.id.register_btn_jump)
    public void onButtonJumpClick() {
        presenter.jumpRegistration();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_register_photo;
    }

}
