package com.example.rahul.sih;

import android.graphics.Color;
import android.util.Pair;

import com.nightonke.boommenu.BoomButtons.ButtonPlaceEnum;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.Piece.PiecePlaceEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Weiping Huang at 23:44 on 16/11/21
 * For Personal Open Source
 * Contact me at 2584541288@qq.com or nightonke@outlook.com
 * For more projects: https://github.com/Nightonke
 */
public class BuilderManager {

    private static int[] imageResources = new int[]{
            R.drawable.bat,
            R.drawable.bear,
            R.drawable.bee,
            R.drawable.butterfly,
            R.drawable.cat,
            R.drawable.deer,
            R.drawable.dolphin,
            R.drawable.eagle,
            R.drawable.horse,
            R.drawable.elephant,
            R.drawable.owl,
            R.drawable.peacock,
            R.drawable.pig,
            R.drawable.rat,
            R.drawable.snake,
            R.drawable.squirrel
    };

    private static int imageResourceIndex = 0;

    static int getImageResource() {
        if (imageResourceIndex >= imageResources.length) imageResourceIndex = 0;
        return imageResources[imageResourceIndex++];
    }



    static HamButton.Builder getHamButtonBuilder() {
        return new HamButton.Builder()
                .normalImageRes(getImageResource())
                .normalTextRes(R.string.text_ham_button_text_normal)
                .subNormalTextRes(R.string.text_ham_button_sub_text_normal);
    }

    static HamButton.Builder getHamButtonBuilder(String text, String subText) {
        return new HamButton.Builder()
                .normalImageRes(getImageResource())
                .normalText(text)
                .subNormalText(subText);
    }

    static HamButton.Builder getPieceCornerRadiusHamButtonBuilder() {
        return new HamButton.Builder()
                .normalImageRes(getImageResource())
                .normalTextRes(R.string.text_ham_button_text_normal)
                .subNormalTextRes(R.string.text_ham_button_sub_text_normal);
    }

    static HamButton.Builder getHamButtonBuilderWithDifferentPieceColor() {
        return new HamButton.Builder()
                .normalImageRes(getImageResource())
                .normalTextRes(R.string.text_ham_button_text_normal)
                .subNormalTextRes(R.string.text_ham_button_sub_text_normal)
                .pieceColor(Color.WHITE);
    }

    static List<String> getCircleButtonData(ArrayList<Pair> piecesAndButtons) {
        List<String> data = new ArrayList<>();
        for (int p = 0; p < PiecePlaceEnum.values().length - 1; p++) {
            for (int b = 0; b < ButtonPlaceEnum.values().length - 1; b++) {
                PiecePlaceEnum piecePlaceEnum = PiecePlaceEnum.getEnum(p);
                ButtonPlaceEnum buttonPlaceEnum = ButtonPlaceEnum.getEnum(b);
                if (piecePlaceEnum.pieceNumber() == buttonPlaceEnum.buttonNumber()
                        || buttonPlaceEnum == ButtonPlaceEnum.Horizontal
                        || buttonPlaceEnum == ButtonPlaceEnum.Vertical) {
                    piecesAndButtons.add(new Pair<>(piecePlaceEnum, buttonPlaceEnum));
                    data.add(piecePlaceEnum + " " + buttonPlaceEnum);
                    if (piecePlaceEnum == PiecePlaceEnum.HAM_1
                            || piecePlaceEnum == PiecePlaceEnum.HAM_2
                            || piecePlaceEnum == PiecePlaceEnum.HAM_3
                            || piecePlaceEnum == PiecePlaceEnum.HAM_4
                            || piecePlaceEnum == PiecePlaceEnum.HAM_5
                            || piecePlaceEnum == PiecePlaceEnum.HAM_6
                            || piecePlaceEnum == PiecePlaceEnum.Share
                            || piecePlaceEnum == PiecePlaceEnum.Custom
                            || buttonPlaceEnum == ButtonPlaceEnum.HAM_1
                            || buttonPlaceEnum == ButtonPlaceEnum.HAM_2
                            || buttonPlaceEnum == ButtonPlaceEnum.HAM_3
                            || buttonPlaceEnum == ButtonPlaceEnum.HAM_4
                            || buttonPlaceEnum == ButtonPlaceEnum.HAM_5
                            || buttonPlaceEnum == ButtonPlaceEnum.HAM_6
                            || buttonPlaceEnum == ButtonPlaceEnum.Custom) {
                        piecesAndButtons.remove(piecesAndButtons.size() - 1);
                        data.remove(data.size() - 1);
                    }
                }
            }
        }
        return data;
    }

    static List<String> getHamButtonData(ArrayList<Pair> piecesAndButtons) {
        List<String> data = new ArrayList<>();
        for (int p = 0; p < PiecePlaceEnum.values().length - 1; p++) {
            for (int b = 0; b < ButtonPlaceEnum.values().length - 1; b++) {
                PiecePlaceEnum piecePlaceEnum = PiecePlaceEnum.getEnum(p);
                ButtonPlaceEnum buttonPlaceEnum = ButtonPlaceEnum.getEnum(b);
                if (piecePlaceEnum.pieceNumber() == buttonPlaceEnum.buttonNumber()
                        || buttonPlaceEnum == ButtonPlaceEnum.Horizontal
                        || buttonPlaceEnum == ButtonPlaceEnum.Vertical) {
                    piecesAndButtons.add(new Pair<>(piecePlaceEnum, buttonPlaceEnum));
                    data.add(piecePlaceEnum + " " + buttonPlaceEnum);
                    if (piecePlaceEnum.getValue() < PiecePlaceEnum.HAM_1.getValue()
                            || piecePlaceEnum == PiecePlaceEnum.Share
                            || piecePlaceEnum == PiecePlaceEnum.Custom
                            || buttonPlaceEnum.getValue() < ButtonPlaceEnum.HAM_1.getValue()) {
                        piecesAndButtons.remove(piecesAndButtons.size() - 1);
                        data.remove(data.size() - 1);
                    }
                }
            }
        }
        return data;
    }

    private static BuilderManager ourInstance = new BuilderManager();

    public static BuilderManager getInstance() {
        return ourInstance;
    }

    private BuilderManager() {
    }
}
