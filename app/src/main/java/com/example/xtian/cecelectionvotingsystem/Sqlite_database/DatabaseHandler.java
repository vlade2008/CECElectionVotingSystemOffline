package com.example.xtian.cecelectionvotingsystem.Sqlite_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.xtian.cecelectionvotingsystem.getSetterItem.item_candidates;
import com.example.xtian.cecelectionvotingsystem.getSetterItem.item_complete;
import com.example.xtian.cecelectionvotingsystem.getSetterItem.item_party;
import com.example.xtian.cecelectionvotingsystem.getSetterItem.item_position;
import com.example.xtian.cecelectionvotingsystem.getSetterItem.item_sqlite;
import com.example.xtian.cecelectionvotingsystem.getSetterItem.item_views_candidates;
import com.example.xtian.cecelectionvotingsystem.getSetterItem.item_vote;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    public static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "cecelectionManager";

    //VIEW
    private static final String VIEWS_NAME = "candidate_details";
    // table name
	private static final String TABLE_STUDENTS = "students";
    private static final String TABLE_POSITION = "position";
    private static final String TABLE_CANDIDATES = "candidates";
    private static final String TABLE_PARTY = "party";
    private static final String TABLE_COMPLETE = "complete";
    private static final String TABLE_VOTE = "vote";

	// Contacts Table Columns names of students
	//student columns
	private static final String KEYSTUDENT_NO = "student_No";
	private static final String KEYSTUDENT_ID = "student_ID";
	private static final String KEYSTUDENT_NAME = "student_Name";
	private static final String KEYSTUDENT_COURSE = "student_Course";
	private static final String KEYSTUDENT_PASSWORD = "student_Password";
	private static final String KEYSTUDENT_FLAG = "flag";
	//position colums
	private static final String KEYPOSITION_ID = "position_ID";
	private static final String KEYPOSITION_NAME = "position_Name";
    //candidates columns
    private static final String KEYCANDIDATES_ID = "candidate_ID";
    private static final String KEYCANDIDATES_STUDENTID = "student_ID";
    private static final String KEYCANDIDATES_POSITIONID = "position_ID";
    private static final String KEYCANDIDATES_PARTYID = "party_ID";
    private static final String KEYCANDIDATES_VOTENUMBER = "vote_Number";
    private static final String KEYCANDIDATES_COIN = "candidate_coin";
    //party columns
    private static final String KEYPARTY_ID = "party_ID";
    private static final String KEYPARTY_NAME = "party_Name";
    //flag columns
    private static final String KEYCOMPLETE = "complete";
    //vote colums
    private static final String KEYVOtE_STUDENTID = "student_ID";
    private static final String KEYVOtE_PRESIDENTID = "president_ID";
    private static final String KEYVOtE_VICEID = "vicepresident_ID";
    private static final String KEYVOtE_SECRETARYID = "secretary_ID";
    private static final String KEYVOtE_TREASURERID = "treasurer_ID";
    private static final String KEYVOtE_AUDITORID = "auditor_ID";
    private static final String KEYVOtE_PROFIRST = "profirst_ID";
    private static final String KEYVOtE_PROSECOND = "prosecond_ID";
    private static final String KEYVOtE_FIRSTYRID = "firstyr_ID";
    private static final String KEYVOtE_SECONDYRID = "secondyr_ID";
    private static final String KEYVOtE_THIRDYRID = "thirdyr_ID";
    private static final String KEYVOtE_FOURTHYRID = "fourthyr_ID";
    private static final String KEYVOtE_VOTEFLAG = "voteflag";


    //arrylist and getter and setter
	private final ArrayList<item_sqlite> student_list = new ArrayList<item_sqlite>();
    private final ArrayList<item_position> position_list = new ArrayList<item_position>();
    private final ArrayList<item_candidates> candidates_list = new ArrayList<item_candidates>();
    private final ArrayList<item_party> party_list = new ArrayList<item_party>();
    private final ArrayList<item_complete> complete_list = new ArrayList<item_complete>();
    private final ArrayList<item_views_candidates> candidateViews_list = new ArrayList<item_views_candidates>();
    private final ArrayList<item_vote> vote_list = new ArrayList<item_vote>();

    public DatabaseHandler(Context context) {
	super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



	// Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

		String CREATE_CONTACTS_STUDENTS = "CREATE TABLE " + TABLE_STUDENTS + "("
				+ KEYSTUDENT_NO + " INTEGER PRIMARY KEY," + KEYSTUDENT_ID + " TEXT,"
				+ KEYSTUDENT_NAME + " TEXT," + KEYSTUDENT_COURSE + " TEXT,"
				+ KEYSTUDENT_PASSWORD + " TEXT," + KEYSTUDENT_FLAG + " TEXT" + ")";

        String CREATE_POSITION = "CREATE TABLE " + TABLE_POSITION + "("
                + KEYPOSITION_ID + " INTEGER PRIMARY KEY," + KEYPOSITION_NAME + " TEXT" + ")";

        String CREATE_CANDIDATES = "CREATE TABLE " + TABLE_CANDIDATES + "("
                + KEYCANDIDATES_ID + " INTEGER PRIMARY KEY," + KEYCANDIDATES_STUDENTID + " TEXT,"
                + KEYCANDIDATES_POSITIONID + " TEXT," + KEYCANDIDATES_PARTYID + " TEXT,"
                + KEYCANDIDATES_VOTENUMBER + " TEXT," + KEYCANDIDATES_COIN + " TEXT" + ")";

        String CREATE_PARTY = "CREATE TABLE " + TABLE_PARTY + "("
                + KEYPARTY_ID + " INTEGER PRIMARY KEY," + KEYPARTY_NAME + " TEXT" + ")";

        String CREATE_COMPLETE = "CREATE TABLE " + TABLE_COMPLETE + "("+KEYCOMPLETE + " TEXT" + ")";

        String CREATE_VIEW = "CREATE VIEW candidate_details AS SELECT s.student_ID,s.student_Name,s.student_Course, c.position_ID, po.position_Name, c.party_ID, pa.party_Name, c.vote_Number,c.candidate_coin FROM students AS s, candidates AS c, party AS pa, position AS po WHERE s.student_ID=c.student_ID AND c.party_ID=pa.party_ID AND c.position_ID=po.position_ID";

        String CREATE_VOTE = "CREATE TABLE " + TABLE_VOTE + "("
                + KEYVOtE_STUDENTID + " TEXT," + KEYVOtE_PRESIDENTID + " TEXT,"
                + KEYVOtE_VICEID + " TEXT," + KEYVOtE_SECRETARYID + " TEXT,"
                + KEYVOtE_TREASURERID + " TEXT," + KEYVOtE_AUDITORID + " TEXT,"
                + KEYVOtE_PROFIRST + " TEXT," + KEYVOtE_PROSECOND + " TEXT,"
                + KEYVOtE_FIRSTYRID + " TEXT," + KEYVOtE_SECONDYRID + " TEXT,"
                + KEYVOtE_THIRDYRID + " TEXT," + KEYVOtE_FOURTHYRID + " TEXT," + KEYVOtE_VOTEFLAG + " TEXT" + ")";

		db.execSQL(CREATE_CONTACTS_STUDENTS);
        db.execSQL(CREATE_POSITION);
        db.execSQL(CREATE_CANDIDATES);
        db.execSQL(CREATE_PARTY);
        db.execSQL(CREATE_COMPLETE);
        db.execSQL(CREATE_VOTE);
        db.execSQL(CREATE_VIEW);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSITION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CANDIDATES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPLETE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOTE);
        db.execSQL("DROP VIEW IF EXISTS " + VIEWS_NAME);

	// Create tables again
	onCreate(db);
    }


	/**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    //display 1 columun in table
    public item_sqlite check_password(String id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_STUDENTS, new String[]{KEYSTUDENT_ID,
                        KEYSTUDENT_NAME, KEYSTUDENT_PASSWORD, KEYSTUDENT_FLAG}, KEYSTUDENT_ID + "=?",
                new String[]{id}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

         item_sqlite item_sqlite = new item_sqlite(cursor.getString(0),
                cursor.getString(1), cursor.getString(2),cursor.getString(3));
        // return contact
        cursor.close();
        db.close();

        return item_sqlite;
    }

	// Getting  Count from table
	public int check_database() {
		String countQuery = "SELECT  * FROM " + TABLE_STUDENTS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);
		int cnt = cursor.getCount();
		cursor.close();

		// return count
		return cnt;
	}
    public int check_position() {
        String countQuery = "SELECT  * FROM " + TABLE_POSITION;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();

        // return count
        return cnt;
    }
    public int check_Candidates() {
        String countQuery = "SELECT  * FROM " + TABLE_CANDIDATES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();

        // return count
        return cnt;
    }
    public int check_Party() {
        String countQuery = "SELECT  * FROM " + TABLE_PARTY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();

        // return count
        return cnt;
    }
    public int check_Complete() {
        String countQuery = "SELECT  * FROM " + TABLE_COMPLETE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();

        // return count
        return cnt;
    }
    public int check_student(String username, String password) {
        String countQuery = "SELECT  * FROM " + TABLE_STUDENTS +" WHERE student_ID='" + username + "' AND student_Password='" + password + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();

        // return count
        return cnt;
    }//checking log in student
    public int check_PositionCandidates(String position_ID_CHECK) {
        String countQuery = "SELECT  * FROM " + TABLE_CANDIDATES +" WHERE position_ID='" + position_ID_CHECK + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();

        // return count
        return cnt;
    }
    public int check_vote() {
        String countQuery = "SELECT  * FROM " + TABLE_VOTE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();

        // return count
        return cnt;
    }

	//display
	public ArrayList<item_sqlite> Get_Student(){

		try{
			student_list.clear();
			//select query
			String selectquery = "SELECT * FROM "+TABLE_STUDENTS;
			SQLiteDatabase db = this.getWritableDatabase();
			Cursor cursor = db.rawQuery(selectquery,null);

			if (cursor.moveToFirst()){
				do {
					String studentNo = cursor.getString(0);
					String studentID =  cursor.getString(1);
					String studentName = cursor.getString(2);
					String studentCourse = cursor.getString(3);
					String studentPass = cursor.getString(4);
					String studentFlag =  cursor.getString(5);
					student_list.add(new item_sqlite(studentNo, studentID, studentName, studentCourse,studentPass,studentFlag));

				}while (cursor.moveToNext());
			}
			cursor.close();
			db.close();
		}catch (Exception e){
			Log.e("all_contact", "" + e);
		}
		return student_list;
	}
    public ArrayList<item_position> Get_Position(){

        try{
            position_list.clear();
            //select query
            String selectquery = "SELECT * FROM "+TABLE_POSITION;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectquery,null);

            if (cursor.moveToFirst()){
                do {
                    String positionID = cursor.getString(0);
                    String positionName =  cursor.getString(1);
                    position_list.add(new item_position(positionID,positionName));

                }while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch (Exception e){
            Log.e("all_contact", "" + e);
        }
        return position_list;
    }
    public ArrayList<item_candidates> Get_Candidates(){

        try{
            candidates_list.clear();
            //select query
            String selectquery = "SELECT * FROM "+TABLE_CANDIDATES;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectquery,null);

            if (cursor.moveToFirst()){
                do {
                    String candidate_ID = cursor.getString(0);
                    String student_ID =  cursor.getString(1);
                    String position_ID =  cursor.getString(2);
                    String party_ID =  cursor.getString(3);
                    String vote_Number =  cursor.getString(4);
                    String candidate_coin =  cursor.getString(5);
                    candidates_list.add(new item_candidates(candidate_ID,student_ID,position_ID,party_ID,vote_Number,candidate_coin));

                }while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch (Exception e){
            Log.e("all_contact", "" + e);
        }
        return candidates_list;
    }
    public ArrayList<item_party> Get_Party(){

        try{
            party_list.clear();
            //select query
            String selectquery = "SELECT * FROM "+TABLE_PARTY;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectquery,null);

            if (cursor.moveToFirst()){
                do {
                    String party_ID = cursor.getString(0);
                    String party_Name =  cursor.getString(1);
                    party_list.add(new item_party(party_ID,party_Name));

                }while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch (Exception e){
            Log.e("all_contact", "" + e);
        }
        return party_list;
    }
    public ArrayList<item_views_candidates> Get_CandidatesViews(){

        try{
            candidateViews_list.clear();
            //select query
            String selectquery = "SELECT * FROM "+VIEWS_NAME;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectquery,null);

            if (cursor.moveToFirst()){
                do {
                    String student_ID = cursor.getString(0);
                    String student_Name =  cursor.getString(1);
                    String student_Course =  cursor.getString(2);
                    String position_ID =  cursor.getString(3);
                    String position_Name =  cursor.getString(4);
                    String party_ID =  cursor.getString(5);
                    String party_Name =  cursor.getString(6);
                    String vote_Number =  cursor.getString(7);
                    String candidate_coin =  cursor.getString(8);
                    candidateViews_list.add(new item_views_candidates(student_ID, student_Name, student_Course, position_ID, position_Name, party_ID,party_Name,vote_Number,candidate_coin));

                }while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch (Exception e){
            Log.e("all_contact", "" + e);
        }
        return candidateViews_list;
    }
    public ArrayList<item_complete> Get_Complete(){

        try{
            complete_list.clear();
            //select query
            String selectquery = "SELECT * FROM "+TABLE_PARTY;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectquery,null);

            if (cursor.moveToFirst()){
                do {
                    String complete = cursor.getString(0);
                    complete_list.add(new item_complete(complete));

                }while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch (Exception e){
            Log.e("all_contact", "" + e);
        }
        return complete_list;
    }
    public ArrayList<item_views_candidates> Get_Confirm(String id){

        try{
            candidateViews_list.clear();
            //select query
            String selectquery = "SELECT * FROM "+VIEWS_NAME +" WHERE student_ID='" + id + "'";;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectquery,null);

            if (cursor.moveToFirst()){
                do {
                    String student_ID = cursor.getString(0);
                    String student_Name =  cursor.getString(1);
                    String student_Course =  cursor.getString(2);
                    String position_ID =  cursor.getString(3);
                    String position_Name =  cursor.getString(4);
                    String party_ID =  cursor.getString(5);
                    String party_Name =  cursor.getString(6);
                    String vote_Number =  cursor.getString(7);
                    String candidate_coin =  cursor.getString(8);
                    candidateViews_list.add(new item_views_candidates(student_ID, student_Name, student_Course, position_ID, position_Name, party_ID,party_Name,vote_Number,candidate_coin));

                }while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch (Exception e){
            Log.e("all_contact", "" + e);
        }
        return candidateViews_list;
    }
    public ArrayList<item_vote> Get_Vote(){

        try{
            vote_list.clear();
            //select query
            String selectquery = "SELECT * FROM "+TABLE_VOTE;
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectquery,null);

            if (cursor.moveToFirst()){
                do {
                    String studentID =cursor.getString(0);
                    String presidentID=cursor.getString(1);
                    String VicepresidentID=cursor.getString(2);
                    String secretaryID=cursor.getString(3);
                    String tresureID=cursor.getString(4);
                    String auditorID=cursor.getString(5);
                    String proFirstID=cursor.getString(6);
                    String proSecondID=cursor.getString(7);
                    String firstyearID=cursor.getString(8);
                    String secondyearID=cursor.getString(9);
                    String thirdyearID=cursor.getString(10);
                    String fourthyearID=cursor.getString(11);
                    String voteflag = cursor.getString(12);
                    vote_list.add(new item_vote(studentID,presidentID, VicepresidentID, secretaryID, tresureID, auditorID, proFirstID, proSecondID, firstyearID, secondyearID, thirdyearID, fourthyearID,voteflag));

                }while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch (Exception e){
            Log.e("all_contact", "" + e);
        }
        return vote_list;
    }


	//insert
	public void Add_Student(item_sqlite item_sqlite) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		//values.put(KEY_NAME, contact.getName()); // Contact Name
		values.put(KEYSTUDENT_NO, item_sqlite.getStudentNo());
		values.put(KEYSTUDENT_ID, item_sqlite.getStudentId());
		values.put(KEYSTUDENT_NAME, item_sqlite.getStudentName());
		values.put(KEYSTUDENT_COURSE, item_sqlite.getStudentCourse());
		values.put(KEYSTUDENT_PASSWORD, item_sqlite.getStudentPass());
		values.put(KEYSTUDENT_FLAG, item_sqlite.getStudentFlag());
		// Inserting Row
		db.insert(TABLE_STUDENTS, null, values);
		db.close(); // Closing database connection
	}
    public void Add_position(item_position item_position) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put(KEY_NAME, contact.getName()); // Contact Name
        values.put(KEYPOSITION_ID, item_position.getPositionID());
        values.put(KEYPOSITION_NAME, item_position.getPositionName());
        // Inserting Row
        db.insert(TABLE_POSITION, null, values);
        db.close(); // Closing database connection
    }
    public void Add_Candidates(item_candidates item_candidates) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEYCANDIDATES_ID, item_candidates.getCandidate_id());
        values.put(KEYCANDIDATES_STUDENTID, item_candidates.getStudent_id());
        values.put(KEYCANDIDATES_POSITIONID, item_candidates.getPosition_id());
        values.put(KEYCANDIDATES_PARTYID, item_candidates.getParty_id());
        values.put(KEYCANDIDATES_VOTENUMBER, item_candidates.getVote_number());
        values.put(KEYCANDIDATES_COIN, item_candidates.getCandidate_coin());
        // Inserting Row
        db.insert(TABLE_CANDIDATES, null, values);
        db.close(); // Closing database connection
    }
    public void Add_Party(item_party item_party) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEYPARTY_ID, item_party.getParty_id());
        values.put(KEYPARTY_NAME, item_party.getParty_name());
        // Inserting Row
        db.insert(TABLE_PARTY, null, values);
        db.close(); // Closing database connection
    }
    public void Add_Complete(item_complete item_complete) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEYCOMPLETE, item_complete.getComplete());
        // Inserting Row
        db.insert(TABLE_COMPLETE, null, values);
        db.close(); // Closing database connection
    }
    public void Add_vote(item_vote item_vote) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEYVOtE_STUDENTID, item_vote.getStudentID());
        values.put(KEYVOtE_PRESIDENTID, item_vote.getPresidentID());
        values.put(KEYVOtE_VICEID, item_vote.getVicepresidentID());
        values.put(KEYVOtE_SECRETARYID, item_vote.getSecretaryID());
        values.put(KEYVOtE_TREASURERID, item_vote.getTresureID());
        values.put(KEYVOtE_AUDITORID, item_vote.getAuditorID());
        values.put(KEYVOtE_PROFIRST, item_vote.getProFirstID());
        values.put(KEYVOtE_PROSECOND, item_vote.getProSecondID());
        values.put(KEYVOtE_FIRSTYRID, item_vote.getFirstyearID());
        values.put(KEYVOtE_SECONDYRID, item_vote.getSecondyearID());
        values.put(KEYVOtE_THIRDYRID, item_vote.getThirdyearID());
        values.put(KEYVOtE_FOURTHYRID, item_vote.getFourthyearID());
        values.put(KEYVOtE_VOTEFLAG, item_vote.getVoteflag());
        // Inserting Row
        db.insert(TABLE_VOTE, null, values);
        db.close(); // Closing database connection
    }


    //display by position
    public ArrayList<item_views_candidates> Get_President(){

        try{
            candidateViews_list.clear();
            //select query
            String position_candiate = "1";
            String selectquery = "SELECT * FROM "+VIEWS_NAME+" where position_ID="+position_candiate+"";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectquery,null);

            if (cursor.moveToFirst()){
                do {
                    String student_ID = cursor.getString(0);
                    String student_Name =  cursor.getString(1);
                    String student_Course =  cursor.getString(2);
                    String position_ID =  cursor.getString(3);
                    String position_Name =  cursor.getString(4);
                    String party_ID =  cursor.getString(5);
                    String party_Name =  cursor.getString(6);
                    String vote_Number =  cursor.getString(7);
                    String candidate_coin =  cursor.getString(8);
                    candidateViews_list.add(new item_views_candidates(student_ID, student_Name, student_Course, position_ID, position_Name, party_ID,party_Name,vote_Number,candidate_coin));

                }while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch (Exception e){
            Log.e("all_contact", "" + e);
        }
        return candidateViews_list;
    }
    public ArrayList<item_views_candidates> Get_VicePresident(){

        try{
            candidateViews_list.clear();
            //select query
            String position_candiate = "2";
            String selectquery = "SELECT * FROM "+VIEWS_NAME+" where position_ID="+position_candiate+"";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectquery,null);

            if (cursor.moveToFirst()){
                do {
                    String student_ID = cursor.getString(0);
                    String student_Name =  cursor.getString(1);
                    String student_Course =  cursor.getString(2);
                    String position_ID =  cursor.getString(3);
                    String position_Name =  cursor.getString(4);
                    String party_ID =  cursor.getString(5);
                    String party_Name =  cursor.getString(6);
                    String vote_Number =  cursor.getString(7);
                    String candidate_coin =  cursor.getString(8);
                    candidateViews_list.add(new item_views_candidates(student_ID, student_Name, student_Course, position_ID, position_Name, party_ID,party_Name,vote_Number,candidate_coin));

                }while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch (Exception e){
            Log.e("all_contact", "" + e);
        }
        return candidateViews_list;
    }
    public ArrayList<item_views_candidates> Get_Secretary(){

        try{
            candidateViews_list.clear();
            //select query
            String position_candiate = "3";
            String selectquery = "SELECT * FROM "+VIEWS_NAME+" where position_ID="+position_candiate+"";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectquery,null);

            if (cursor.moveToFirst()){
                do {
                    String student_ID = cursor.getString(0);
                    String student_Name =  cursor.getString(1);
                    String student_Course =  cursor.getString(2);
                    String position_ID =  cursor.getString(3);
                    String position_Name =  cursor.getString(4);
                    String party_ID =  cursor.getString(5);
                    String party_Name =  cursor.getString(6);
                    String vote_Number =  cursor.getString(7);
                    String candidate_coin =  cursor.getString(8);
                    candidateViews_list.add(new item_views_candidates(student_ID, student_Name, student_Course, position_ID, position_Name, party_ID,party_Name,vote_Number,candidate_coin));

                }while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch (Exception e){
            Log.e("all_contact", "" + e);
        }
        return candidateViews_list;
    }
    public ArrayList<item_views_candidates> Get_Treasurer(){

        try{
            candidateViews_list.clear();
            //select query
            String position_candiate = "4";
            String selectquery = "SELECT * FROM "+VIEWS_NAME+" where position_ID="+position_candiate+"";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectquery,null);

            if (cursor.moveToFirst()){
                do {
                    String student_ID = cursor.getString(0);
                    String student_Name =  cursor.getString(1);
                    String student_Course =  cursor.getString(2);
                    String position_ID =  cursor.getString(3);
                    String position_Name =  cursor.getString(4);
                    String party_ID =  cursor.getString(5);
                    String party_Name =  cursor.getString(6);
                    String vote_Number =  cursor.getString(7);
                    String candidate_coin =  cursor.getString(8);
                    candidateViews_list.add(new item_views_candidates(student_ID, student_Name, student_Course, position_ID, position_Name, party_ID,party_Name,vote_Number,candidate_coin));

                }while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch (Exception e){
            Log.e("all_contact", "" + e);
        }
        return candidateViews_list;
    }
    public ArrayList<item_views_candidates> Get_Auditor(){

        try{
            candidateViews_list.clear();
            //select query
            String position_candiate = "5";
            String selectquery = "SELECT * FROM "+VIEWS_NAME+" where position_ID="+position_candiate+"";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectquery,null);

            if (cursor.moveToFirst()){
                do {
                    String student_ID = cursor.getString(0);
                    String student_Name =  cursor.getString(1);
                    String student_Course =  cursor.getString(2);
                    String position_ID =  cursor.getString(3);
                    String position_Name =  cursor.getString(4);
                    String party_ID =  cursor.getString(5);
                    String party_Name =  cursor.getString(6);
                    String vote_Number =  cursor.getString(7);
                    String candidate_coin =  cursor.getString(8);
                    candidateViews_list.add(new item_views_candidates(student_ID, student_Name, student_Course, position_ID, position_Name, party_ID,party_Name,vote_Number,candidate_coin));

                }while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch (Exception e){
            Log.e("all_contact", "" + e);
        }
        return candidateViews_list;
    }
    public ArrayList<item_views_candidates> Get_Profirst(){

        try{
            candidateViews_list.clear();
            //select query
            String position_candiate = "6";
            String selectquery = "SELECT * FROM "+VIEWS_NAME+" where position_ID="+position_candiate+"";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectquery,null);

            if (cursor.moveToFirst()){
                do {
                    String student_ID = cursor.getString(0);
                    String student_Name =  cursor.getString(1);
                    String student_Course =  cursor.getString(2);
                    String position_ID =  cursor.getString(3);
                    String position_Name =  cursor.getString(4);
                    String party_ID =  cursor.getString(5);
                    String party_Name =  cursor.getString(6);
                    String vote_Number =  cursor.getString(7);
                    String candidate_coin =  cursor.getString(8);
                    candidateViews_list.add(new item_views_candidates(student_ID, student_Name, student_Course, position_ID, position_Name, party_ID,party_Name,vote_Number,candidate_coin));

                }while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch (Exception e){
            Log.e("all_contact", "" + e);
        }
        return candidateViews_list;
    }
    public ArrayList<item_views_candidates> Get_Firstyr(){

        try{
            candidateViews_list.clear();
            //select query
            String position_candiate = "7";
            String selectquery = "SELECT * FROM "+VIEWS_NAME+" where position_ID="+position_candiate+"";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectquery,null);

            if (cursor.moveToFirst()){
                do {
                    String student_ID = cursor.getString(0);
                    String student_Name =  cursor.getString(1);
                    String student_Course =  cursor.getString(2);
                    String position_ID =  cursor.getString(3);
                    String position_Name =  cursor.getString(4);
                    String party_ID =  cursor.getString(5);
                    String party_Name =  cursor.getString(6);
                    String vote_Number =  cursor.getString(7);
                    String candidate_coin =  cursor.getString(8);
                    candidateViews_list.add(new item_views_candidates(student_ID, student_Name, student_Course, position_ID, position_Name, party_ID,party_Name,vote_Number,candidate_coin));

                }while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch (Exception e){
            Log.e("all_contact", "" + e);
        }
        return candidateViews_list;
    }
    public ArrayList<item_views_candidates> Get_Secondyr(){

        try{
            candidateViews_list.clear();
            //select query
            String position_candiate = "8";
            String selectquery = "SELECT * FROM "+VIEWS_NAME+" where position_ID="+position_candiate+"";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectquery,null);

            if (cursor.moveToFirst()){
                do {
                    String student_ID = cursor.getString(0);
                    String student_Name =  cursor.getString(1);
                    String student_Course =  cursor.getString(2);
                    String position_ID =  cursor.getString(3);
                    String position_Name =  cursor.getString(4);
                    String party_ID =  cursor.getString(5);
                    String party_Name =  cursor.getString(6);
                    String vote_Number =  cursor.getString(7);
                    String candidate_coin =  cursor.getString(8);
                    candidateViews_list.add(new item_views_candidates(student_ID, student_Name, student_Course, position_ID, position_Name, party_ID,party_Name,vote_Number,candidate_coin));

                }while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch (Exception e){
            Log.e("all_contact", "" + e);
        }
        return candidateViews_list;
    }
    public ArrayList<item_views_candidates> Get_Thirdyr(){

        try{
            candidateViews_list.clear();
            //select query
            String position_candiate = "9";
            String selectquery = "SELECT * FROM "+VIEWS_NAME+" where position_ID="+position_candiate+"";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectquery,null);

            if (cursor.moveToFirst()){
                do {
                    String student_ID = cursor.getString(0);
                    String student_Name =  cursor.getString(1);
                    String student_Course =  cursor.getString(2);
                    String position_ID =  cursor.getString(3);
                    String position_Name =  cursor.getString(4);
                    String party_ID =  cursor.getString(5);
                    String party_Name =  cursor.getString(6);
                    String vote_Number =  cursor.getString(7);
                    String candidate_coin =  cursor.getString(8);
                    candidateViews_list.add(new item_views_candidates(student_ID, student_Name, student_Course, position_ID, position_Name, party_ID,party_Name,vote_Number,candidate_coin));

                }while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch (Exception e){
            Log.e("all_contact", "" + e);
        }
        return candidateViews_list;
    }
    public ArrayList<item_views_candidates> Get_Fourthyr(){

        try{
            candidateViews_list.clear();
            //select query
            String position_candiate = "10";
            String selectquery = "SELECT * FROM "+VIEWS_NAME+" where position_ID="+position_candiate+"";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectquery,null);

            if (cursor.moveToFirst()){
                do {
                    String student_ID = cursor.getString(0);
                    String student_Name =  cursor.getString(1);
                    String student_Course =  cursor.getString(2);
                    String position_ID =  cursor.getString(3);
                    String position_Name =  cursor.getString(4);
                    String party_ID =  cursor.getString(5);
                    String party_Name =  cursor.getString(6);
                    String vote_Number =  cursor.getString(7);
                    String candidate_coin =  cursor.getString(8);
                    candidateViews_list.add(new item_views_candidates(student_ID, student_Name, student_Course, position_ID, position_Name, party_ID,party_Name,vote_Number,candidate_coin));

                }while (cursor.moveToNext());
            }
            cursor.close();
            db.close();
        }catch (Exception e){
            Log.e("all_contact", "" + e);
        }
        return candidateViews_list;
    }

    // Updating single
    public int Update_Student(String id) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEYSTUDENT_FLAG, "1");
        // updating row
        return db.update(TABLE_STUDENTS, values, KEYSTUDENT_ID + " = ?",
                new String[] { id });
    }

    //delete sqlite
    public void drop_all() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSITION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CANDIDATES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PARTY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPLETE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VOTE);
        db.execSQL("DROP VIEW IF EXISTS " + VIEWS_NAME);
        // Create tables again
        onCreate(db);
        db.close(); // Closing database connection
    }
    // Deleting single contact
    public void Delete_Vote(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_VOTE, KEYVOtE_STUDENTID + " = ?",
                new String[]{id});
        db.close();
    }
}
