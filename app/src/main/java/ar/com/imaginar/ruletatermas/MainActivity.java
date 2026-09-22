package ar.com.imaginar.ruletatermas;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        TextView view = new TextView(this);
        view.setBackgroundColor(Color.rgb(0, 142, 160));
        view.setTextColor(Color.WHITE);
        view.setTextSize(26f);
        view.setGravity(Gravity.CENTER);
        view.setPadding(40, 40, 40, 40);
        view.setText("R U L E T A   T E R M A S\n\nPRUEBA NATIVA OK\n\nAndroid " + Build.VERSION.RELEASE + "  ·  API " + Build.VERSION.SDK_INT + "\n\nSi esta pantalla permanece abierta, el APK nativo funciona correctamente.");
        setContentView(view);

        try {
            getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            );
        } catch (Throwable ignored) {}
    }
}
