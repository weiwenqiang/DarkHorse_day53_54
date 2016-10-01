package com.wwq.toggle;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.wwq.darkhorseday53.R;

/**
 * Created by 魏文强 on 2016/9/20.
 */
public class GlideToggleActivity extends Activity {
    private ToggleButton toggleButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day53_glide_toggle);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton);

        toggleButton.setSlideBackgroudResource(R.drawable.slide_button);
        toggleButton.setSwitchBackgroudResource(R.drawable.switch_background);
        toggleButton.setToggleState(ToggleButton.ToggleState.Open);

        toggleButton.setOnToggleStateChangListener(new ToggleButton.OnToggleStateChangeListener() {
            @Override
            public void onToggleStateChange(ToggleButton.ToggleState state) {
                Toast.makeText(GlideToggleActivity.this, state == ToggleButton.ToggleState.Open?"开启":"关闭",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
