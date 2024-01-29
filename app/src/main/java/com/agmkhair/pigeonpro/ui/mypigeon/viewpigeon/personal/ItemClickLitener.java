package com.agmkhair.pigeonpro.ui.mypigeon.viewpigeon.personal;

import android.view.View;

import com.agmkhair.pigeonpro.ui.model.Birds;

import java.util.List;

public interface ItemClickLitener
{
    void onClick(View view, int position, List<Birds>  birds);
}
