package com.agmkhair.pigeonpro.ui;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.agmkhair.pigeonpro.ui.model.Birds;
import com.agmkhair.pigeonpro.ui.model.Member;

import java.util.ArrayList;
import java.util.List;

public class Variable
{

    public static List<Birds> allBirds = new ArrayList<>();
    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static FirebaseAuth adminAuth ; // = FirebaseAuth.getInstance();
    public static DatabaseReference adminDatabase = FirebaseDatabase.getInstance().getReference().child("Clubs");
    public static DatabaseReference clubBirds = FirebaseDatabase.getInstance().getReference().child("ClubBirds");
    public static DatabaseReference userDB= FirebaseDatabase.getInstance().getReference().child("Users");
    public static DatabaseReference birdDB = FirebaseDatabase.getInstance().getReference().child("Birds");
    public static String[] adminId ;
    public static List<Birds> listPersinalBirds = new ArrayList<>();
    //= adminAuth.getCurrentUser().getEmail().toString().split("@");
    public static long  clubMemberNumber = 0;
    public static String clubTeg;
    public static Member memberInfo;
    public static Member birdOwner;
    public static String code = null;
    public static String userId = null;
    public static String mVerificationId = null;
    //public static int mVerificationId;
    public static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public static PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

}
