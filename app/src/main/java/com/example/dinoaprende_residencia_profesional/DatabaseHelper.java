package com.example.dinoaprende_residencia_profesional;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.dinoaprende_residencia_profesional.DatabaseContract.*;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DinoAprende.db";
    private static final int DATABASE_VERSION = 1;

    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SCORE = "score";

    public static class UserProfileTable {
        public static final String TABLE_NAME = "user_profile";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_SCORE = "score";
        public static final String COLUMN_PROFILE_PICTURE = "profile_picture";
    }

    private static  DatabaseHelper instance;
    private SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_CATEGORIES_TABLE = "CREATE TABLE " +
                CategoriesTable.TABLE_NAME + "( " +
                CategoriesTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoriesTable.COLUMN_NAME + " TEXT " +
                ")";

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " +
                QuestionsTable.TABLE_NAME + " ( " +
                QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER_NR + " INTEGER, " +
                QuestionsTable.COLUMN_DIFFICULTY + " TEXT, " +
                QuestionsTable.COLUMN_CATEGORY_ID + " INTEGER, " +
                "FOREIGN KEY(" + QuestionsTable.COLUMN_CATEGORY_ID + ") REFERENCES " +
                CategoriesTable.TABLE_NAME + "(" + CategoriesTable._ID + ")" + "ON DELETE CASCADE" +
                ")";

        final String SQL_CREATE_USER_PROFILE_TABLE = "CREATE TABLE " +
                UserProfileTable.TABLE_NAME + " ( " +
                UserProfileTable.COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UserProfileTable.COLUMN_NAME + " TEXT, " +
                UserProfileTable.COLUMN_SCORE + " INTEGER, " +
                UserProfileTable.COLUMN_PROFILE_PICTURE + " TEXT" +
                ")";

        final String SQL_CREATE_DINO_FRIENDS_INFO_TABLE = "CREATE TABLE " +
                DinoFriendsInfoTable.TABLE_NAME + " ( " +
                DinoFriendsInfoTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                DinoFriendsInfoTable.COLUMN_DINO_NAME + " TEXT, " +
                DinoFriendsInfoTable.COLUMN_DINO_SPECIE + " TEXT, " +
                DinoFriendsInfoTable.COLUMN_DINO_PERIOD + " TEXT, " +
                DinoFriendsInfoTable.COLUMN_DINO_DIET + " TEXT, " +
                DinoFriendsInfoTable.COLUMN_DINO_TEMPERAMENT + " TEXT, " +
                DinoFriendsInfoTable.COLUMN_DINO_DESCRIPTION + " TEXT, " +
                DinoFriendsInfoTable.COLUMN_DINO_PHOTO + " TEXT, " +
                DinoFriendsInfoTable.COLUMN_DINO_SCORE + " INTEGER" +
                ")";

        db.execSQL(SQL_CREATE_CATEGORIES_TABLE);
        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        db.execSQL(SQL_CREATE_USER_PROFILE_TABLE);
        db.execSQL(SQL_CREATE_DINO_FRIENDS_INFO_TABLE);
        fillCategoriesTable();
        fillQuestionsTable();
        fillDinoFriendsInfoTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CategoriesTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + UserProfileTable.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DinoFriendsInfoTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillCategoriesTable() {
        Category c1 = new Category("Sumas");
        addCategory(c1);
        Category c2 = new Category("Restas");
        addCategory(c2);
        Category c3 = new Category("Multiplicaciones");
        addCategory(c3);
        Category c4 = new Category("Divisiones");
        addCategory(c4);
        Category c5 = new Category("Mixtas");
        addCategory(c5);
    }

    private void addCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put(CategoriesTable.COLUMN_NAME, category.getName());
        db.insert(CategoriesTable.TABLE_NAME, null, cv);
    }

    // Inserta un nuevo usuario en la base de datos
    public boolean addUser(String name, String profileImage) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(UserProfileTable.COLUMN_PROFILE_PICTURE, profileImage); // Cambio aquí
        values.put(COLUMN_SCORE, 0);
        long result = db.insert(UserProfileTable.TABLE_NAME, null, values);
        db.close();
        return result != -1;
    }

    private void fillQuestionsTable() {
        // |====================| SUMAS |====================| \\
        // |--------------| DIFICULTAD: FÁCIL |--------------| \\
        Question q1 = new Question("¿Cuánto es 1+1?",
                "2", "5", "3", 1,
                Question.DIFFICULTY_EASY, Category.ADDITIONS);
        addQuestion(q1);
        Question q2 = new Question("¿Cuánto es 3+1?",
                "7", "1", "4", 3,
                Question.DIFFICULTY_EASY, Category.ADDITIONS);
        addQuestion(q2);
        Question q3 = new Question("¿Cuánto es 2+1?",
                "2", "5", "3", 3,
                Question.DIFFICULTY_EASY, Category.ADDITIONS);
        addQuestion(q3);
        Question q4 = new Question("¿Cuánto es 1+2?",
                "1", "3", "5", 2,
                Question.DIFFICULTY_EASY, Category.ADDITIONS);
        addQuestion(q4);
        Question q5 = new Question("¿Cuánto es 0+2?",
                "1", "0", "2", 3,
                Question.DIFFICULTY_EASY, Category.ADDITIONS);
        addQuestion(q5);

        // |--------------| DIFICULTAD: MEDIA |--------------| \\
        Question q6 = new Question("¿Cuánto es 5+0?",
                "0", "5", "4", 2,
                Question.DIFFICULTY_MEDIUM, Category.ADDITIONS);
        addQuestion(q6);
        Question q7 = new Question("¿Cuánto es 6+1?",
                "9", "7", "8", 2,
                Question.DIFFICULTY_MEDIUM, Category.ADDITIONS);
        addQuestion(q7);
        Question q8 = new Question("¿Cuánto es 8+0+1?",
                "11", "7", "9", 3,
                Question.DIFFICULTY_MEDIUM, Category.ADDITIONS);
        addQuestion(q8);
        Question q9 = new Question("¿Cuánto es 9+2?",
                "11", "7", "9", 1,
                Question.DIFFICULTY_MEDIUM, Category.ADDITIONS);
        addQuestion(q9);
        Question q10 = new Question("¿Cuánto es 7+3?",
                "11", "10", "15", 2,
                Question.DIFFICULTY_MEDIUM, Category.ADDITIONS);
        addQuestion(q10);

        // |--------------| DIFICULTAD: DIFICIL |--------------| \\
        Question q11 = new Question("¿Cuánto es 3+1+1?",
                "5", "7", "6", 1,
                Question.DIFFICULTY_HARD, Category.ADDITIONS);
        addQuestion(q11);
        Question q12 = new Question("¿Cuánto es 7+0+2?",
                "9", "7", "0", 1,
                Question.DIFFICULTY_HARD, Category.ADDITIONS);
        addQuestion(q12);
        Question q13 = new Question("¿Cuánto es 4+2+3?",
                "10", "4", "9", 3,
                Question.DIFFICULTY_HARD, Category.ADDITIONS);
        addQuestion(q13);
        Question q14 = new Question("¿Cuánto es 2+10?",
                "11", "12", "15", 2,
                Question.DIFFICULTY_HARD, Category.ADDITIONS);
        addQuestion(q14);
        Question q15 = new Question("¿Cuánto es 15+1?",
                "12", "15", "16", 3,
                Question.DIFFICULTY_HARD, Category.ADDITIONS);
        addQuestion(q15);


        // |====================| RESTAS |====================| \\
        // |--------------| DIFICULTAD: FÁCIL |--------------| \\
        Question q16 = new Question("¿Cuánto es 1-1?",
                "2", "0", "1", 2,
                Question.DIFFICULTY_EASY, Category.SUBTRACTIONS);
        addQuestion(q16);
        Question q17 = new Question("¿Cuánto es 2-1?",
                "4", "3", "1", 3,
                Question.DIFFICULTY_EASY, Category.SUBTRACTIONS);
        addQuestion(q17);
        Question q18 = new Question("¿Cuánto es 5-2?",
                "4", "3", "1", 2,
                Question.DIFFICULTY_EASY, Category.SUBTRACTIONS);
        addQuestion(q18);
        Question q19 = new Question("¿Cuánto es 3-2?",
                "1", "5", "3", 1,
                Question.DIFFICULTY_EASY, Category.SUBTRACTIONS);
        addQuestion(q19);
        Question q20 = new Question("¿Cuánto es 4-3?",
                "7", "2", "1", 3,
                Question.DIFFICULTY_EASY, Category.SUBTRACTIONS);
        addQuestion(q20);

        // |--------------| DIFICULTAD: MEDIA |--------------| \\
        Question q21 = new Question("¿Cuánto es 9-3?",
                "6", "9", "15", 1,
                Question.DIFFICULTY_MEDIUM, Category.SUBTRACTIONS);
        addQuestion(q21);
        Question q22 = new Question("¿Cuánto es 5-4?",
                "7", "9", "1", 3,
                Question.DIFFICULTY_MEDIUM, Category.SUBTRACTIONS);
        addQuestion(q22);
        Question q23 = new Question("¿Cuánto es 8-4?",
                "4", "5", "7", 1,
                Question.DIFFICULTY_MEDIUM, Category.SUBTRACTIONS);
        addQuestion(q23);
        Question q24 = new Question("¿Cuánto es 10-3-1?",
                "10", "9", "6", 3,
                Question.DIFFICULTY_MEDIUM, Category.SUBTRACTIONS);
        addQuestion(q24);
        Question q25 = new Question("¿Cuánto es 5-1?",
                "3", "4", "5", 2,
                Question.DIFFICULTY_MEDIUM, Category.SUBTRACTIONS);
        addQuestion(q25);

        // |--------------| DIFICULTAD: DIFICIL |--------------| \\
        Question q26 = new Question("¿Cuánto es 15-5-2?",
                "4", "8", "7", 2,
                Question.DIFFICULTY_HARD, Category.SUBTRACTIONS);
        addQuestion(q26);
        Question q27 = new Question("¿Cuánto es 12-10?",
                "5", "2", "9", 2,
                Question.DIFFICULTY_HARD, Category.SUBTRACTIONS);
        addQuestion(q27);
        Question q28 = new Question("¿Cuánto es 13-3?",
                "8", "12", "10", 3,
                Question.DIFFICULTY_HARD, Category.SUBTRACTIONS);
        addQuestion(q28);
        Question q29 = new Question("¿Cuánto es 10-9-1?",
                "7", "1", "0", 3,
                Question.DIFFICULTY_HARD, Category.SUBTRACTIONS);
        addQuestion(q29);
        Question q30 = new Question("¿Cuánto es 20-10-1?",
                "9", "10", "15", 1,
                Question.DIFFICULTY_HARD, Category.SUBTRACTIONS);
        addQuestion(q30);

        // |===============| MULTIPLICACIONES |===============| \\
        // |--------------| DIFICULTAD: FÁCIL |--------------| \\
        Question q31 = new Question("¿Cuánto es 1x1?",
                "1", "0", "2", 1,
                Question.DIFFICULTY_EASY, Category.MULTIPLICATIONS);
        addQuestion(q31);
        Question q32 = new Question("¿Cuánto es 2x1?",
                "3", "2", "5", 2,
                Question.DIFFICULTY_EASY, Category.MULTIPLICATIONS);
        addQuestion(q32);
        Question q33 = new Question("¿Cuánto es 1x7?",
                "7", "10", "9", 1,
                Question.DIFFICULTY_EASY, Category.MULTIPLICATIONS);
        addQuestion(q33);
        Question q34 = new Question("¿Cuánto es 4x2?",
                "9", "6", "8", 3,
                Question.DIFFICULTY_EASY, Category.MULTIPLICATIONS);
        addQuestion(q34);
        Question q35 = new Question("¿Cuánto es 2x2?",
                "7", "4", "3", 2,
                Question.DIFFICULTY_EASY, Category.MULTIPLICATIONS);
        addQuestion(q35);

        // |--------------| DIFICULTAD: MEDIA |--------------| \\
        Question q36 = new Question("¿Cuánto es 3x2?",
                "6", "4", "3", 1,
                Question.DIFFICULTY_MEDIUM, Category.MULTIPLICATIONS);
        addQuestion(q36);
        Question q37 = new Question("¿Cuánto es 4x6?",
                "20", "15", "24", 3,
                Question.DIFFICULTY_MEDIUM, Category.MULTIPLICATIONS);
        addQuestion(q37);
        Question q38 = new Question("¿Cuánto es 5x1?",
                "6", "7", "5", 3,
                Question.DIFFICULTY_MEDIUM, Category.MULTIPLICATIONS);
        addQuestion(q38);
        Question q39 = new Question("¿Cuánto es 6x2?",
                "16", "12", "14", 2,
                Question.DIFFICULTY_MEDIUM, Category.MULTIPLICATIONS);
        addQuestion(q39);
        Question q40 = new Question("¿Cuánto es 3x7?",
                "21", "17", "25", 1,
                Question.DIFFICULTY_MEDIUM, Category.MULTIPLICATIONS);
        addQuestion(q40);

        // |--------------| DIFICULTAD: DIFICIL |--------------| \\
        Question q41 = new Question("¿Cuánto es 8x2?",
                "14", "18", "16", 3,
                Question.DIFFICULTY_HARD, Category.MULTIPLICATIONS);
        addQuestion(q41);
        Question q42 = new Question("¿Cuánto es 7x9?",
                "64", "69", "63", 3,
                Question.DIFFICULTY_HARD, Category.MULTIPLICATIONS);
        addQuestion(q42);
        Question q43 = new Question("¿Cuánto es 9x3?",
                "29", "27", "25", 2,
                Question.DIFFICULTY_HARD, Category.MULTIPLICATIONS);
        addQuestion(q43);
        Question q44 = new Question("¿Cuánto es 8x9?",
                "72", "80", "75", 1,
                Question.DIFFICULTY_HARD, Category.MULTIPLICATIONS);
        addQuestion(q44);
        Question q45 = new Question("¿Cuánto es 7x7?",
                "50", "49", "45", 2,
                Question.DIFFICULTY_HARD, Category.MULTIPLICATIONS);
        addQuestion(q45);

        // |===============| DIVISIONES |===============| \\
        // |--------------| DIFICULTAD: FÁCIL |--------------| \\
        Question q46 = new Question("¿Cuánto es 6÷2?",
                "1", "2", "3", 3,
                Question.DIFFICULTY_EASY, Category.DIVISIONS);
        addQuestion(q46);
        Question q47 = new Question("¿Cuánto es 8÷4?",
                "4", "1", "2", 3,
                Question.DIFFICULTY_EASY, Category.DIVISIONS);
        addQuestion(q47);
        Question q48 = new Question("¿Cuánto es 10÷5?",
                "2", "6", "4", 1,
                Question.DIFFICULTY_EASY, Category.DIVISIONS);
        addQuestion(q48);
        Question q49 = new Question("¿Cuánto es 10÷2?",
                "4", "5", "6", 2,
                Question.DIFFICULTY_EASY, Category.DIVISIONS);
        addQuestion(q49);
        Question q50 = new Question("¿Cuánto es 4÷2?",
                "5", "2", "4", 2,
                Question.DIFFICULTY_EASY, Category.DIVISIONS);
        addQuestion(q50);

        // |--------------| DIFICULTAD: MEDIA |--------------| \\
        Question q51 = new Question("¿Cuánto es 16÷4?",
                "8", "5", "4", 3,
                Question.DIFFICULTY_MEDIUM, Category.DIVISIONS);
        addQuestion(q51);
        Question q52 = new Question("¿Cuánto es 18÷3?",
                "6", "9", "3", 1,
                Question.DIFFICULTY_MEDIUM, Category.DIVISIONS);
        addQuestion(q52);
        Question q53 = new Question("¿Cuánto es 9÷3?",
                "3", "4", "7", 1,
                Question.DIFFICULTY_MEDIUM, Category.DIVISIONS);
        addQuestion(q53);
        Question q54 = new Question("¿Cuánto es 15÷5?",
                "6", "3", "9", 2,
                Question.DIFFICULTY_MEDIUM, Category.DIVISIONS);
        addQuestion(q54);
        Question q55 = new Question("¿Cuánto es 12÷4?",
                "3", "4", "5", 1,
                Question.DIFFICULTY_MEDIUM, Category.DIVISIONS);
        addQuestion(q55);

        // |--------------| DIFICULTAD: DIFICIL |--------------| \\
        Question q56 = new Question("¿Cuánto es 20÷5?",
                "5", "6", "8", 1,
                Question.DIFFICULTY_HARD, Category.DIVISIONS);
        addQuestion(q56);
        Question q57 = new Question("¿Cuánto es 25÷5?",
                "7", "6", "5", 3,
                Question.DIFFICULTY_HARD, Category.DIVISIONS);
        addQuestion(q57);
        Question q58 = new Question("¿Cuánto es 14÷7?",
                "2", "4", "3", 1,
                Question.DIFFICULTY_HARD, Category.DIVISIONS);
        addQuestion(q58);
        Question q59 = new Question("¿Cuánto es 18÷6?",
                "5", "3", "4", 2,
                Question.DIFFICULTY_HARD, Category.DIVISIONS);
        addQuestion(q59);
        Question q60 = new Question("¿Cuánto es 21÷3?",
                "6", "8", "7", 3,
                Question.DIFFICULTY_HARD, Category.DIVISIONS);
        addQuestion(q60);

        // |===============| MIXTAS |===============| \\
        // |--------------| DIFICULTAD: FÁCIL |--------------| \\
        Question q61 = new Question("¿Cuánto es 3+4?",
                "8", "7", "3", 2,
                Question.DIFFICULTY_EASY, Category.MIXED);
        addQuestion(q61);
        Question q62 = new Question("¿Cuánto es 6-2?",
                "7", "5", "4", 3,
                Question.DIFFICULTY_EASY, Category.MIXED);
        addQuestion(q62);
        Question q63 = new Question("¿Cuánto es 2x3?",
                "4", "6", "5", 2,
                Question.DIFFICULTY_EASY, Category.MIXED);
        addQuestion(q63);
        Question q64 = new Question("¿Cuánto es 8÷4?",
                "2", "5", "3", 1,
                Question.DIFFICULTY_EASY, Category.MIXED);
        addQuestion(q64);
        Question q65 = new Question("¿Cuánto es 4+5-2?",
                "9", "10", "7", 3,
                Question.DIFFICULTY_EASY, Category.MIXED);
        addQuestion(q65);

        // |--------------| DIFICULTAD: MEDIA |--------------| \\
        Question q66 = new Question("¿Cuánto es 7+6-4?",
                "9", "10", "8", 1,
                Question.DIFFICULTY_MEDIUM, Category.MIXED);
        addQuestion(q66);
        Question q67 = new Question("¿Cuánto es 5x3+2?",
                "19", "15", "17", 3,
                Question.DIFFICULTY_MEDIUM, Category.MIXED);
        addQuestion(q67);
        Question q68 = new Question("¿Cuánto es 10-4x2?",
                "8", "2", "12", 2,
                Question.DIFFICULTY_MEDIUM, Category.MIXED);
        addQuestion(q68);
        Question q69 = new Question("¿Cuánto es 12÷4+3?",
                "7", "9", "6", 1,
                Question.DIFFICULTY_MEDIUM, Category.MIXED);
        addQuestion(q69);
        Question q70 = new Question("¿Cuánto es 8+4÷2?",
                "8", "6", "10", 3,
                Question.DIFFICULTY_MEDIUM, Category.MIXED);
        addQuestion(q70);

        // |--------------| DIFICULTAD: DIFICIL |--------------| \\
        Question q71 = new Question("¿Cuánto es 14+7x2-10?",
                "32", "18", "20", 2,
                Question.DIFFICULTY_HARD, Category.MIXED);
        addQuestion(q71);
        Question q72 = new Question("¿Cuánto es 20−8÷2+5×3?",
                "30", "40", "25", 2,
                Question.DIFFICULTY_HARD, Category.MIXED);
        addQuestion(q72);
        Question q73 = new Question("¿Cuánto es 21÷3+12−4×2?",
                "13", "9", "15", 1,
                Question.DIFFICULTY_HARD, Category.MIXED);
        addQuestion(q73);
        Question q74 = new Question("¿Cuánto es 10×2−4÷2+8?",
                "28", "24", "26", 3,
                Question.DIFFICULTY_HARD, Category.MIXED);
        addQuestion(q74);
        Question q75 = new Question("¿Cuánto es 15+10÷2−3×4?",
                "9", "13", "15", 1,
                Question.DIFFICULTY_HARD, Category.MIXED);
        addQuestion(q75);
    }

    private void fillDinoFriendsInfoTable(){
        DinoFriend dinoFriend1 = new DinoFriend("Bebé Blue, la velociraptor", "Velociraptor mongoliensis", "Cretácico Tardío", "Carnívoro",
                "Pasivo", "Te presento a Blue, la primera bebé de velociraptor de la pandilla. Su nombre se debe a su característica franja azul que recorre todo su cuerpo. Es juguetona, y a lo largo de su entrenamiento, se han notado niveles de preocupación y amor hacia su entrenador Owen.\n\nSe dice que es la más inteligente de sus hermanas, y, sobre todo, cariñosa con su entrenador.",
                "dino_friend1", 10);
        addDinoFriendInfo(dinoFriend1);
        DinoFriend dinoFriend2 = new DinoFriend("Bebé Charlie, la velociraptor", "Velociraptor mongoliensis", "Cretácico Tardío", "Carnívoro",
                "Pasivo", "Ella es Bebé Charlie, una de las integrantes de la pandilla de velociraptores. Es una de las hermanas de Blue, pero al contrario de ella, Charlie es muy inquieta e hiperactiva. Siempre tiene hambre y molesta a sus hermanas.\n\nSu entrenador Owen está ganando un lazo muy fuerte con toda la pandilla, se ha dado cuenta que, a pesar de lo inquietas que pueden ser, la inteligencia que poseen es interesante.",
                "dino_friend2", 20);
        addDinoFriendInfo(dinoFriend2);
        DinoFriend dinoFriend3 = new DinoFriend("Bebé Delta, la velociraptor", "Velociraptor mongoliensis", "Cretácico Tardío", "Carnívoro",
                "Pasivo", "Continuando con las integrantes de la pandilla de velociraptores, ella es la tercera de las hermanas, su nombre es Delta. A diferencia de las demás, Owen ha podido notar que Delta posee una agilidad y velocidad sorprendentes a pesar de su corta edad.\n\nSe dice que cuando sea mayor, Delta será la más rápida de sus 3 hermanas.",
                "dino_friend3", 30);
        addDinoFriendInfo(dinoFriend3);
        DinoFriend dinoFriend4 = new DinoFriend("Bebé Echo, la velociraptor", "Velociraptor mongoliensis", "Cretácico Tardío", "Carnívoro",
                "Pasivo", "Ella es Echo, la tercera hermana de Blue y última integrante de la pandilla de velociraptores. Ella fue la última en nacer y es la integrante a quien se le ha notado un mejor desarrollo auditivo. Esta habilidad podría ayudarla en un futuro para la caza y protección contra algún depredador.\n\nEcho se lleva muy bien con su hermana Charlie, ya que son igual de inquietas y juguetonas. Cuando las 4 sean mayores, estamos seguros de que serán una increíble manada.",
                "dino_friend4", 40);
        addDinoFriendInfo(dinoFriend4);
        DinoFriend dinoFriend5 = new DinoFriend("Bebé Dinamita, la Braquiosaurus", "Brachiosaurus altithorax", "Jurásico Tardío", "Hervíboro",
                "Pasivo", "Ella es Dinamita, una bebé Brachiosaurio muy juguetona. Es muy difícil que se esté quieta. Duerme muy poco debido a que todo el tiempo cree que es hora de jugar; su juego favorito es “las escondidas” y le encantan las bayas rojas.\n\nLos Brachiosaurios se caracterizan por ser dinosaurios herbívoros y de cuellos enormes cuando son adultos. Suelen defenderse de sus depredadores con su larga cola y enormes patas. No querrías meterte con un dinosaurio de estos.",
                "dino_friend5", 50);
        addDinoFriendInfo(dinoFriend5);
        DinoFriend dinoFriend6 = new DinoFriend("Rebel, el pequeño híbrido", "Sino-Spino", "Holoceno (actualidad)", "Hervíboro",
                "Pasivo", "¿Has escuchado la palabra “híbrido”? Híbrido quiere decir “mezcla” o “combinación” de 2 cosas o más dentro de un mismo objeto. Este es el caso de “Rebel”, un pequeñ hibrido que fue creado a base de dos especies distintas de dinosaurio: un grande y fuerte Espinosaurio y un tranquilo y pacífico Sinoceratops.\n\nSe caracteriza por ser muy juguetón con su hermana “Angel”. Se complementan uno con el otro, y si no están juntos, la diversión no es la misma.",
                "dino_friend6", 70);
        addDinoFriendInfo(dinoFriend6);
        DinoFriend dinoFriend7 = new DinoFriend("Bumpy, la anquilosaurio", "Ankylosaurus", "Holoceno (actualidad)", "Hervíboro",
                "Pasivo", "Bumpy es una anquilosaurio que nació con un cuerno más grande que el otro. Los científicos que la crearon la consideraron una anomalía, pero nosotros pensamos que es hermosa.\nSu caparazón tiene colores característicos, al igual que su piel\n\nLos anquilosaurios se caracterizan por su enorme cola que la utilizaban para defenderse de otros depredadores, al igual que su caparazón parecido al de una tortuga, rodeado de enormes púas.",
                "dino_friend7", 80);
        addDinoFriendInfo(dinoFriend7);
        DinoFriend dinoFriend8 = new DinoFriend("Dimorphodon", "Dimorphodon macronyx", "Jurásico Temprano", "Carnívoro",
                "Agresivo", "El Dimorphodon fue un dinosaurio volador que vivió hace mucho tiempo. No era realmente un dinosaurio, sino un pterosaurio, ¡un primo de los dinosaurios que podía volar! Tenía un cuerpo pequeño, pero una cabeza grande y dos tipos diferentes de dientes en su boca, de ahí su nombre que significa \"dos formas de dientes\". Imagina un animal del tamaño de un cuervo, con alas y una cola larga, ¡y tendrás una idea de cómo lucía el Dimorphodon!",
                "dino_friend8", 90);
        addDinoFriendInfo(dinoFriend8);
        DinoFriend dinoFriend9 = new DinoFriend("Dilophosaurio", "Dilophosaurus watherilli", "Jurásico Temprano", "Carnívoro",
                "Agresivo", "El Dilophosaurus era un dinosaurio carnívoro que vivió durante el período Jurásico. Tenía una cresta doble en su cabeza, como dos abanicos, lo que lo hacía ver muy especial. A pesar de que en algunas películas se le muestra escupiendo veneno, no hay evidencia real de que pudiera hacer eso. ¡Piensa en un dinosaurio del tamaño de un carro con un sombrero festivo en la cabeza!",
                "dino_friend9", 100);
        addDinoFriendInfo(dinoFriend9);
        DinoFriend dinoFriend10 = new DinoFriend("Parasaurio", "P. walkeri", "Cretácico Tardío", "Hervíboro",
                "Pasivo", "Este amigable herbívoro masticaba plantas con su pico sin dientes. Su larga cresta podría haberle ayudado a comunicarse con sonidos, ¡como una trompeta gigante! Solían viajar en manadas y sus crestas no solo servían para comunicarse, sino para defenderse otros depredadores también.",
                "dino_friend10", 110);
        addDinoFriendInfo(dinoFriend10);

        DinoFriend dinoFriend11 = new DinoFriend("Pachy", "Pachycephalosaurus wyomingensis", "Cretácico Tardío", "Hervíboro",
                "Pasivo", "Con una dieta a base de plantas, este dinosaurio tenía una cabeza dura que quizás usaba para chocar con otros Pachys en competencias o para defenderse de depredadores. No eran agresivos, pero si los provocaban, era mejor correr.",
                "dino_friend11", 130);
        addDinoFriendInfo(dinoFriend11);
        DinoFriend dinoFriend12 = new DinoFriend("Baryonyx", "Baryonyx walkeri", "Cretácico Temprano", "Carnívoro",
                "Agresivo", "Era un cazador especializado: con sus garras y dientes afilados, atrapaba peces en los ríos. ¡El pescador experto de la era de los dinosaurios! Era un dinosaurio solitario y, sobre todo, muy territorial, no querrías acercarte a el por nada en el mundo, ¡y tampoco tomar alguno de sus peces!",
                "dino_friend12", 140);
        addDinoFriendInfo(dinoFriend12);
        DinoFriend dinoFriend13 = new DinoFriend("Pierce, el Kentrosaurio", "Kentrosaurus aethiopicus", "Jurásico Tardío", "Herbívoro",
                "Pasivo", "Pierce es un Kentroaurio muy tranquilo, pero que se asusta con facilidad; es una especie de dinosaurio hervíboro que come plantas. Es pariente del Estegosaurio y tiene picos afilados en su cuerpo para protegerse de los carnívoros. Su entrenadora estuvo trabajando en una investigación para poder tratar de comunicarse con él, ¡y lo ha logrado!",
                "dino_friend13", 150);
        addDinoFriendInfo(dinoFriend13);
        DinoFriend dinoFriend14 = new DinoFriend("Toro, el Carnotauro", "Carnotaurus sastrei", "Cretácico Tardío", "Carnívoro",
                "Agresivo", "Toro, un Carnotauro con cuernos pequeños y una mandíbula poderosa, le encanta correr y embestir a sus presas o amenazas. Es un dinosaurio bastante veloz a pesar de su tamaño y muy ruidoso, siempre lo escucharás venir, no es nada sigiloso. Su dieta se basa en carne, por lo que debes tener cuidado.",
                "dino_friend14", 180);
        addDinoFriendInfo(dinoFriend14);
        DinoFriend dinoFriend15 = new DinoFriend("Pteranodon", "Peteranodon longiceps", "Cretácico", "Carnívoro",
                "Agresivo", "El Pteranodon se caracterizaba por sus enormes alas que le permitían volar y planear largas distancias sin ningún descanso. Su dieta es a base de carne, sobre todo, de peces. Lograba atraparlos con su enorme y puntiagudo pico sin dientes; era agresivo y territorial. Viajaba en grandes manadas y le era fácil escapar de depredadores.",
                "dino_friend15", 200);
        addDinoFriendInfo(dinoFriend15);
        DinoFriend dinoFriend16 = new DinoFriend("Charlie, la velociraptor", "Velociraptor mongoliensis", "Cretácico Tardío", "Carnívoro",
                "Agresivo", "Ha pasado un tiempo, tanto, que los integrantes de la pandilla de velociraptores, ¡ya son adultos! ¿Recuerdas a Charlie? De bebé fue muy inquieta, pero ahora que es mayor, ¡lo sigue siendo! Su actitud nunca cambió, pero de igual manera, así la queremos.\n\nCharlie ha desarrollado una increíble velocidad y sentido del olfato, ella podría detectar tu olor a kilómetros de distancia, esto le ayudaría a cazar para conseguir alimento.",
                "dino_friend16", 250);
        addDinoFriendInfo(dinoFriend16);
        DinoFriend dinoFriend17 = new DinoFriend("Delta, la velociraptor", "Velociraptor mongoliensis", "Cretácico Tardío", "Carnívoro",
                "Agresivo", "Te presento a Delta. Ha crecido mucho desde la última vez que la vimos, ¿no lo crees? Ahora que es adulta, se caracteriza por su hermoso color de piel verdoso con ligeras franjas negras.\nConforme fue avanzando su crecimiento, mejoró mucho su agilidad y velocidad, ¡lo que la hace la integrante de la pandilla más veloz de las 4!",
                "dino_friend17", 270);
        addDinoFriendInfo(dinoFriend17);
        DinoFriend dinoFriend18 = new DinoFriend("Echo, la velociraptor", "Velociraptor mongoliensis", "Cretácico Tardío", "Carnívoro",
                "Agresivo", "Continuamos con Echo, la integrante #3 de la pandilla. Como puedes notar, ahora que es mayor, ha desarrollado un notable color naranja en su piel, complementado con bastantes franjas negras a lo largo de su cuerpo, ¡parece un tigre! ¿No lo crees? Las habilidades que ha desarrollado durante su entrenamiento son el sigilo y sorprendentes saltos para atrapar a su presa. ¡Definitivamente ella sobreviviría sin la ayuda de sus hermanas!",
                "dino_friend18", 290);
        addDinoFriendInfo(dinoFriend18);
        DinoFriend dinoFriend19 = new DinoFriend("Blue, la velociraptor", "Velociraptor mongoliensis", "Cretácico Tardío", "Carnívoro",
                "Agresivo", "La última integrante de la pandilla, Blue. Desde que era bebé, se caracteriza por su franja azul que recorre su cuerpo. Todos supondrían que, ahora que es adulta, se ha vuelto agresiva con cualquiera que se le acerque, y si, lo es porque protege su territorio, excepto con su entrenador Owen; ellos 2 hacen un excelente equipo.\n\nSe sabe que Blue se ha vuelto la líder de la manada, y haría cualquier cosa por proteger a sus hermanas y a su entrenador.",
                "dino_friend19", 310);
        addDinoFriendInfo(dinoFriend19);
        DinoFriend dinoFriend20 = new DinoFriend("Mosasaurio", "Mosasaurus maximus", "Cretácico Tardío", "Carnívoro",
                "Agresivo", "Se dice que este animal acuático era el rey de los océanos. Tenía un tamaño increíble, más grande que un Megalodón. Este reptil marino se alimentaba de peces y otros animales marinos; una vez que tenía a su presa entre sus dientes, le era imposible escapar. Su enorme cola le ayudaba a propulsarse sobre el agua, mientras que sus aletas eran útiles para conducir.",
                "dino_friend20", 320);
        addDinoFriendInfo(dinoFriend20);

        DinoFriend dinoFriend21 = new DinoFriend("Quetzal", "Quetzalcoatlus northropi", "Cretácico Tardío", "Carnívoro",
                "Agresivo", "Su nombre completo es “Quetzalcoatlus”, llamado así por la deidad azteca Quetzalcóatl, la serpiente emplumada, es un género de pterosaurios pterodactiloideos, y el segundo de los animales voladores más grandes conocidos de todos los tiempos, superado por el Hatzegopteryx.\n\nPodía llegar a medir hasta 12 metros de largo. Se dice que se alimentaban de carne, lo cual es increíble que estos animales pudieron masticar carne con un pico que no contaba con dientes.",
                "dino_friend21", 350);
        addDinoFriendInfo(dinoFriend21);
        DinoFriend dinoFriend22 = new DinoFriend("Rexy, la Tiranosaurio Rex", "Tyrannosaurus rex", "Cretácico Tardío", "Carnívoro",
                "Agresivo", "Conoce a Rexy, la poderosa Tiranosaurio Rex, también conocida como “Roberta”. Ella es conocida por sus cicatrices que lleva en todo el cuerpo, resultado de todas las aventuras y enfrentamientos que ha tenido a lo largo de los años.\nLos Tiranosaurio Rex son enormes dinosaurios carnívoros que se alimentaban de carne, eran solitarios y territoriales, podían llegar a medir hasta 16 metros de largo y 8 metros de alto, ¿te imaginas ver uno en la vida real?",
                "dino_friend22", 380);
        addDinoFriendInfo(dinoFriend22);
        DinoFriend dinoFriend23 = new DinoFriend("Therizinosaurio", "Therizinosaurus cheloniformis", "Cretácico Tardío", "Omnívoro",
                "Agresivo", "El Therizinosaurio, un dinosaurio conocido por sus largas y afiladas garras que le permitían defender su territorio. Este animal es omnívoro, es decir, se alimentaba de carne y plantas.\nPodía llegar a medir hasta 10 metros de largo y 5 metros de altura, con un peso de 3 toneladas. Debido a lo territorial que podía ser, no querrías encontrarte con uno de ellos",
                "dino_friend23", 400);
        addDinoFriendInfo(dinoFriend23);
        DinoFriend dinoFriend24 = new DinoFriend("Espinosaurio", "Spinosaurus aegyptiacus", "Cretáceo Inferior", "Carnívoro",
                "Agresivo", "El poderoso e intimidante Espinosaurio. Se dice era más grande que el Rex y que eran rivales. Podían llegar a medir hasta 18 metros de longitud y pesar de 7 a 9 toneladas. Su característica vela que tenían en sus espaldas les permitían desplazarse por el agua, ayudándolos a ser veloces cazar con mayor facilidad. No se sabe con exactitud si se alimentaba de animales terrestres o peces, pero lo que sí, es que era carnívoro y muy agresivo.",
                "dino_friend24", 450);
        addDinoFriendInfo(dinoFriend24);
        DinoFriend dinoFriend25 = new DinoFriend("Giganotosaurio", "Giganotosaurus carolinii", "Cretácico Medio", "Carnívoro",
                "Agresivo", "Te presento al Giganotosaurio. Su nombre quiere decir “reptil gigante del sur”. Se dice que este gran carnívoro podía llegar a medir entre 13 y 14 metros de longitud y entre 5 a 9 metros de altura. Pesaba entre 6.5 y 11 toneladas, ¡vaya que era pesado! Su apariencia era parecida a la de un Rex debido a que caminaba en 2 patas e igual contaba con 2 brazos delanteros que le permitían dar fuertes rasguños.",
                "dino_friend25", 500);
        addDinoFriendInfo(dinoFriend25);
        DinoFriend dinoFriend26 = new DinoFriend("Scorpios Rex", "Scorpius rex", "Holoceno (Actualidad)", "Carnívoro",
                "Agresivo", "También conocido como “Experimento E750”, no es un dinosaurio que realmente existió, sino que fue creado genéticamente por el Dr. Wu. Su nombre quiere decir “Rey Escorpión”, y fue creado a base de ADN de Tiranosaurio Rex, Velociraptor, Carnotauro, entre otras especies. Se caracteriza por sus espinas que lleva por todo el cuerpo que utiliza para defenderse, ¡cuidado que son venenosas! Se sabe que la luz lo hipnotiza.",
                "dino_friend26", 550);
        addDinoFriendInfo(dinoFriend26);
        DinoFriend dinoFriend27 = new DinoFriend("Indominus Rex", "Indominus rex", "Holoceno (Actualidad)", "Carnívoro",
                "Agresivo", "Ella es la “Indominus Rex”, que nombre tan intimidante, ¿no es así? Su nombre quiere decir “Rey Indomable”, lo que lo hace aún más intimidante. Es una especie creada genéticamente con el ADN de otras especies, al igual que el “Scorpios Rex”. Este monstruo es conocida por su notable color blanco de su piel, sus enormes brazos y garras, y sus temibles dientes. Su comportamiento es extremadamente agresivo y se alimenta de carne.",
                "dino_friend27", 600);
        addDinoFriendInfo(dinoFriend27);
        DinoFriend dinoFriend28 = new DinoFriend("Indominus Rex 2da generación", "Indominus rex", "Holoceno (Actualidad)", "Carnívoro",
                "Agresivo", "Si la Indominus Rex te intimidó, espera a que te presente la generación 2 de este animal. Igual de temible que la original, pero más fuerte y ágil que cualquier otro dinosaurio, caracterizada por su patrón de color rojo que recorre todo su cuerpo, desde su cabeza, hasta su cola. Ningún animal ni dinosaurio es rival para esta segunda Indominus. Debido a su temperamento, actualmente se encuentra en cautiverio.",
                "dino_friend28", 700);
        addDinoFriendInfo(dinoFriend28);
        DinoFriend dinoFriend29 = new DinoFriend("Indoraptor", "Indominus Rex + Utahraptor", "Holoceno (Actualidad)", "Carnívoro",
                "Agresivo", "El Indoraptor, una especie que muchos no la toman como un dinosaurio, sino como un monstruo. Fue creado para utilizarlo como arma biológica y armamento militar, esto para remplazar armamento militar y soldados, mala idea, ¿verdad? Fue creado a base de ADN de Indominus Rex, Velociraptor y un mamífero que se desconoce. Su temperamento era extremadamente agresivo, pero su inteligencia era inigualable, ¡era más inteligente que Blue!",
                "dino_friend29", 800);
        addDinoFriendInfo(dinoFriend29);
        DinoFriend dinoFriend30 = new DinoFriend("Indoraptor 2da generación", "Indominus Rex + Utahraptor", "Holoceno (Actualidad)", "Carnívoro",
                "Agresivo", "Increíble que hayas llegado hasta acá, felicidades! Veo que te gustan los dinosaurios. Te presento al último de la lista: El Indoraptor generación 2. Si, leíste bien, generación 2. Más fuerte, más veloz, el doble de inteligente que su primera generación. Caracterizada por su piel color blanco con su franja de color azul que recorre todo su cuerpo. Fue creada genéticamente por el doctor Henry Wu mediante el uso de ADN de Indominus Rex y la velociraptor Blue.",
                "dino_friend30", 900);
        addDinoFriendInfo(dinoFriend30);
    }

    private void addDinoFriendInfo(DinoFriend dinoFriend){
        ContentValues cv = new ContentValues();
        cv.put(DinoFriendsInfoTable.COLUMN_DINO_NAME, dinoFriend.getDinoName());
        cv.put(DinoFriendsInfoTable.COLUMN_DINO_SPECIE, dinoFriend.getDinoSpecie());
        cv.put(DinoFriendsInfoTable.COLUMN_DINO_PERIOD, dinoFriend.getDinoPeriod());
        cv.put(DinoFriendsInfoTable.COLUMN_DINO_DIET, dinoFriend.getDinoDiet());
        cv.put(DinoFriendsInfoTable.COLUMN_DINO_TEMPERAMENT, dinoFriend.getDinoTemperament());
        cv.put(DinoFriendsInfoTable.COLUMN_DINO_DESCRIPTION, dinoFriend.getDinoDescription());
        cv.put(DinoFriendsInfoTable.COLUMN_DINO_PHOTO, dinoFriend.getDinoPhoto());
        cv.put(DinoFriendsInfoTable.COLUMN_DINO_SCORE, dinoFriend.getDinoScore());
        db.insert(DinoFriendsInfoTable.TABLE_NAME, null, cv);
    }

    private void addQuestion(Question question) {
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_ANSWER_NR, question.getAnswerNr());
        cv.put(QuestionsTable.COLUMN_DIFFICULTY, question.getDifficulty());
        cv.put(QuestionsTable.COLUMN_CATEGORY_ID, question.getCategoryID());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    @SuppressLint("Range")
    public ArrayList<Question> getQuestions(String difficulty, int categoryID) {
        ArrayList<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();

        String[] selectionArgs = new String[]{difficulty, String.valueOf(categoryID)};
        Cursor c = db.rawQuery("SELECT * FROM " + QuestionsTable.TABLE_NAME +
                " WHERE " + QuestionsTable.COLUMN_DIFFICULTY + " = ? AND " +
                QuestionsTable.COLUMN_CATEGORY_ID + " = ?", selectionArgs);

        if (c.moveToFirst()) {
            do {
                Question question = new Question();
                question.setId(c.getInt(c.getColumnIndex(QuestionsTable._ID)));
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setAnswerNr(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER_NR)));
                question.setDifficulty(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_DIFFICULTY)));
                question.setCategoryID(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_CATEGORY_ID)));
                questionList.add(question);
            } while (c.moveToNext());
        }

        c.close();
        return questionList;
    }

    @SuppressLint("Range")
    public DinoFriend getDinoFriendFromDatabase(int id) {
        DinoFriend dinoFriend = null;
        db = getReadableDatabase();

        Cursor c = db.rawQuery("SELECT * FROM " + DinoFriendsInfoTable.TABLE_NAME +
                " WHERE " + DinoFriendsInfoTable._ID + " = ?", new String[]{String.valueOf(id)});

        if (c.moveToFirst()) {
            String dinoName = c.getString(c.getColumnIndex(DinoFriendsInfoTable.COLUMN_DINO_NAME));
            String dinoSpecie = c.getString(c.getColumnIndex(DinoFriendsInfoTable.COLUMN_DINO_SPECIE));
            String dinoPeriod = c.getString(c.getColumnIndex(DinoFriendsInfoTable.COLUMN_DINO_PERIOD));
            String dinoDiet = c.getString(c.getColumnIndex(DinoFriendsInfoTable.COLUMN_DINO_DIET));
            String dinoTemperament = c.getString(c.getColumnIndex(DinoFriendsInfoTable.COLUMN_DINO_TEMPERAMENT));
            String dinoDescription = c.getString(c.getColumnIndex(DinoFriendsInfoTable.COLUMN_DINO_DESCRIPTION));
            String dinoPhoto = c.getString(c.getColumnIndex(DinoFriendsInfoTable.COLUMN_DINO_PHOTO));
            int dinoScore = c.getInt(c.getColumnIndex(DinoFriendsInfoTable.COLUMN_DINO_SCORE));

            dinoFriend = new DinoFriend(dinoName, dinoSpecie, dinoPeriod, dinoDiet,
                    dinoTemperament, dinoDescription, dinoPhoto, dinoScore);
        }

        c.close();
        return dinoFriend;
    }

    public Cursor getUserData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + UserProfileTable.TABLE_NAME;
        return db.rawQuery(query, null);
    }

    @SuppressLint("Range")
    public void updateUserScore(int additionalScore) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(UserProfileTable.TABLE_NAME, new String[]{UserProfileTable.COLUMN_SCORE},
                UserProfileTable.COLUMN_ID + "=?", new String[]{"1"},
                null, null, null);
        int currentScore = 0;
        if (cursor != null && cursor.moveToFirst()) {
            currentScore = cursor.getInt(cursor.getColumnIndex(UserProfileTable.COLUMN_SCORE));
            cursor.close();
        }

        currentScore += additionalScore;

        ContentValues cv = new ContentValues();
        cv.put(UserProfileTable.COLUMN_SCORE, currentScore);

        db.update(UserProfileTable.TABLE_NAME, cv, UserProfileTable.COLUMN_ID + " = ?", new String[]{"1"});
    }
}