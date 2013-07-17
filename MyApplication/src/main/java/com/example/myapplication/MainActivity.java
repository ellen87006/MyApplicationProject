package com.example.myapplication;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
public class MainActivity extends Activity {
    private TextView TextView01, TextView02 ;
    private SensorManager mSensorManager;
    static float[] init = new float[] { 0.0f, 0.0f, 0.0f };
    static float[] values = new float[3];
    float tmp;
    ImageView image;
    long lastUpdateTime = 0;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView01 = (TextView) findViewById(R.id.TextView01);
        TextView02 = (TextView) findViewById(R.id.TextView02);
    /* 取得SensorManager */
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

    }

    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
    /* 取得方向性的Sensor，並註冊SensorEventListener */



        mSensorManager.registerListener(mSensorEventListener, mSensorManager
                .getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION),
                SensorManager.SENSOR_DELAY_NORMAL);

        mSensorManager.registerListener(mSensorEventListener, mSensorManager
                .getDefaultSensor(Sensor.TYPE_GYROSCOPE),
                SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    protected void onPause()
    {
        // TODO Auto-generated method stub
    /* 取消註冊SensorEventListener */
        mSensorManager.unregisterListener(mSensorEventListener);
        super.onPause();
    }

    private final SensorEventListener mSensorEventListener = new SensorEventListener()
    {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy)
        {
            // TODO Auto-generated method stub

        }

        @Override
        public void onSensorChanged(SensorEvent event)
        {
            // TODO Auto-generated method stub
      /* 判斷Sensor的種類 */
            image = (ImageView) findViewById(R.id.imageView1);

            if (event.sensor.getType() == Sensor.TYPE_LINEAR_ACCELERATION)
            {
                if (init[0] == 0 && init[1] == 0 && init[2] == 0) {
                    init[0] = event.values[0];
                    init[1] = event.values[1];
                    init[2] = event.values[2];
                }
        /* TYPE_ACCELEROMETER */
                values = event.values;
                float x_data = values[0];
                float y_data =values[1];
                float z_data =values[2];
                if (z_data<0) {
                    if(tmp>0.5&&z_data<-0.5)
                    {
                        image.setImageResource(R.drawable.back);
                        tmp=0;
                    }
                    else if(z_data<-0.5)
                    {
                        tmp=z_data;
                    }
                }
                else if (z_data>0) {
                    if(tmp<-0.5&&z_data>0.5)
                    {
                        image.setImageResource(R.drawable.go);
                        tmp=0;
                    }
                    else if(z_data>0.5)
                    {
                        tmp=z_data;
                    }
                } else {
                    TextView01.setText("TYPE_ACCELEROMETER: \n\r"+String.valueOf(x_data)+"\n\r"+String.valueOf(y_data)+"\n\r"+String.valueOf(z_data)+"\n\r" );

                }



            }
            else if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE)
            {
        /* TYPE_GYROSCOPE */
                float x_data = event.values[SensorManager.DATA_X];
                float y_data = event.values[SensorManager.DATA_Y];
                float z_data = event.values[SensorManager.DATA_Z];
                TextView02.setText("TYPE_GYROSCOPE: \n\r"+String.valueOf(x_data)+"\n\r"+String.valueOf(y_data)+"\n\r"+String.valueOf(z_data)+"\n\r" );

                if(z_data<-1)
                {
                    image.setImageResource(R.drawable.right);
                }
                else if(z_data>1)
                {
                    image.setImageResource(R.drawable.left);
                }
            }

        }
    };
}

