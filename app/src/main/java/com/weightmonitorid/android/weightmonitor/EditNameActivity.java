package com.weightmonitorid.android.weightmonitor;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.weightmonitorid.android.weightmonitor.helper.Constants;

public class EditNameActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mNameEditText;
    private Button mChangeButton;
    private String goods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_name);

        mNameEditText = (EditText) findViewById(R.id.name_edit_text);
        mChangeButton = (Button) findViewById(R.id.change_button);

        if (getIntent() != null) {
            goods = getIntent().getExtras().getString(Constants.GOODS);
        }

        getSharedPreferences();

        mChangeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mChangeButton){
            String goodsName = mNameEditText.getText().toString().trim();
            savePreference(goodsName);
        }
    }

    private void getSharedPreferences() {
        if (goods.equals("1")) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String electronicName = sharedPreferences.getString(Constants.GOODS_KEY_1, "Barang 1");
            mNameEditText.setText(electronicName);
        } else if (goods.equals("2")) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String electronicName = sharedPreferences.getString(Constants.GOODS_KEY_2, "Barang 2");
            mNameEditText.setText(electronicName);
        } else if (goods.equals("3")) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String electronicName = sharedPreferences.getString(Constants.GOODS_KEY_3, "Barang 3");
            mNameEditText.setText(electronicName);
        } else if (goods.equals("4")) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String electronicName = sharedPreferences.getString(Constants.GOODS_KEY_4, "Barang 4");
            mNameEditText.setText(electronicName);
        }  else if (goods.equals("5")) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String electronicName = sharedPreferences.getString(Constants.GOODS_KEY_5, "Barang 5");
            mNameEditText.setText(electronicName);
        }
    }

    private void savePreference(String goodsName) {
        if (goods.equals("1")) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.GOODS_KEY_1, goodsName);
            editor.apply();
        } else if (goods.equals("2")) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.GOODS_KEY_2, goodsName);
            editor.apply();
        } else if (goods.equals("3")) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.GOODS_KEY_3, goodsName);
            editor.apply();
        } else if (goods.equals("4")) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.GOODS_KEY_4, goodsName);
            editor.apply();
        } else if (goods.equals("5")) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(Constants.GOODS_KEY_5, goodsName);
            editor.apply();
        }
        finish();
    }
}
