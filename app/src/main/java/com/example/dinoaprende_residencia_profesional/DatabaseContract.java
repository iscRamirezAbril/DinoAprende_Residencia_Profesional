package com.example.dinoaprende_residencia_profesional;

import android.provider.BaseColumns;

public final class DatabaseContract {

    private DatabaseContract() {
    }

    public static class CategoriesTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_categories";
        public static final String COLUMN_NAME = "name";
    }

    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "quiz_questions";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static final String COLUMN_OPTION3 = "option3";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
        public static final String COLUMN_DIFFICULTY = "difficulty";
        public static final String COLUMN_CATEGORY_ID = "category_id";
    }

    public static class DinoFriendsInfoTable implements BaseColumns {
        public static final String TABLE_NAME = "dino_friends_info";
        public static final String COLUMN_DINO_NAME = "dinoName";
        public static final String COLUMN_DINO_SPECIE = "dinoSpecie";
        public static final String COLUMN_DINO_PERIOD = "dinoPeriod";
        public static final String COLUMN_DINO_DIET = "dinoDiet";
        public static final String COLUMN_DINO_TEMPERAMENT = "dinoTemperament";
        public static final String COLUMN_DINO_DESCRIPTION = "dinoDescription";
        public static final String COLUMN_DINO_PHOTO = "dinoPhoto";
        public static final String COLUMN_DINO_SCORE = "dinoScore";
    }
}