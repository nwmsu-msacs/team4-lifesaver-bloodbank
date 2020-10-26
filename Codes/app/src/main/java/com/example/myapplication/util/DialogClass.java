package com.example.myapplication.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;




public class DialogClass {
private static Dialog dialog;

    public static void showDialogMethod(final Context context, String message, String title, final Class className){
         dialog=new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_layout);

        TextView titleTv=(TextView)dialog.findViewById(R.id.titleTV);
        TextView messageTv=(TextView)dialog.findViewById(R.id.messageTv);
        Button okBtn=(Button) dialog.findViewById(R.id.okBtn);

        if(title==null)
            titleTv.setVisibility(View.GONE);
        else
            titleTv.setText(title);

        messageTv.setText(message);

        dialog.show();
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(className!=null){
                    context.startActivity(new Intent(context,className));
                    dialog.dismiss();

                    SharedPreference sharedPreference=new SharedPreference(context);
                    sharedPreference.setPrefValue(ApplicationHolder.USERNAME,"");

                   
                    ((Activity)context).finish();

                }else{
                    dialog.dismiss();
                }

            }
        });



    }
}
