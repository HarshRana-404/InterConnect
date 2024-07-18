package com.harsh.interconnect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class RegistrationActivity extends AppCompatActivity {

    String states[] = {"Select State", "Andaman and Nicobar", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh", "Chhattisgarh", "Dadranagar Haveli", "Diu and Daman", "Delhi", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Ladakh", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Puducherry", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal"};
    String city[] = {"Select a state first!"};

    String s0[] = {"Select City", "Port Blair"};
    String s1[] = {"Select City", "Adoni", "Amaravati", "Anantapur", "Chandragiri", "Chittoor", "Dowlaiswaram", "Eluru", "Guntur", "Kadapa", "Kakinada", "Kurnool", "Machilipatnam", "Nagarjunakonda", "Rajahmundry", "Srikakulam", "Tirupati", "Vijayawada", "Visakhapatnam", "Vizianagaram", "Yemmiganur"};
    String s2[] = {"Select City", "Itanagar"};
    String s3[] = {"Select City", "Dhuburi", "Dibrugarh", "Dispur", "Guwahati", "Jorhat", "Nagaon", "Sivasagar", "Silchar", "Tezpur", "Tinsukia"};
    String s4[] = {"Select City", "Ara", "Barauni", "Begusarai", "Bettiah", "Bhagalpur", "Bihar", "Sharif", "Bodh", "Gaya", "Buxar", "Chapra", "Darbhanga", "Dehri", "Dinapur", "Nizamat", "Gaya", "Hajipur", "Jamalpur", "Katihar", "Madhubani", "Motihari", "Munger", "Muzaffarpur", "Patna", "Purnia", "Pusa", "Saharsa", "Samastipur", "Sasaram", "Sitamarhi", "Siwan"};
    String s5[] = {"Select City", "Chandigarh"};
    String s6[] = {"Select City", "Ambikapur", "Bhilai", "Bilaspur", "Dhamtari", "Durg", "Jagdalpur", "Raipur", "Rajnandgaon"};
    String s7[] = {"Select City", "Dadranagar Haveli"};
    String s8[] = {"Select City", "Diu and Daman"};
    String s9[] = {"Select City", "Delhi", "New Delhi"};
    String s10[] = {"Select City", "Madgaon", "Panaji"};
    String s11[] = {"Select City", "Ahmedabad", "Amreli", "Bharuch", "Bhavnagar", "Bhuj", "Dwarka", "Gandhinagar", "Godhra", "Jamnagar", "Junagadh", "Kandla", "Khambhat", "Kheda", "Mahesana", "Morbi", "Nadiad", "Navsari", "Okha", "Palanpur", "Patan", "Porbandar", "Rajkot", "Surat", "Surendranagar", "Valsad", "Veraval"};
    String s12[] = {"Select City", "Ambala", "Bhiwani", "Chandigarh", "Faridabad", "Firozpur", "Jhirka", "Gurugram", "Hansi", "Hisar", "Jind", "Kaithal", "Karnal", "Kurukshetra", "Panipat", "Pehowa", "Rewari", "Rohtak", "Sirsa", "Sonipat"};
    String s13[] = {"Select City", "Bilaspur", "Chamba", "Dalhousie", "Dharmshala", "Hamirpur", "Kangra", "Kullu", "Mandi", "Nahan", "Shimla", "Una"};
    String s14[] = {"Select City", "Anantnag", "Baramula", "Doda", "Gulmarg", "Jammu", "Kathua", "Punch", "Rajouri", "Srinagar", "Udhampur"};
    String s15[] = {"Select City", "Bokaro", "Chaibasa", "Deoghar", "Dhanbad", "Dumka", "Giridih", "Hazaribag", "Jamshedpur", "Jharia", "Rajmahal", "Ranchi", "Saraikela"};
    String s16[] = {"Select City", "Badami", "Ballari", "Bengaluru", "Belagavi", "Bhadravati", "Bidar", "Chikkamagaluru", "Chitradurga", "Davangere", "Halebid", "Hassan", "Hubballi-Dharwad", "Kalaburagi", "Kolar", "Madikeri", "Mandya", "Mangaluru", "Mysuru", "Raichur", "Shivamogga", "Shravanabelagola", "Shrirangapattana", "Tumakuru", "Vijayapura" };
    String s17[] = {"Select City", "Alappuzha", "VatakaraIdukki", "Kannur", "Kochi", "KollamKottayam", "Kozhikode", "Mattancheri", "Palakkad", "Thalassery", "Thiruvananthapuram", "Thrissur" };
    String s18[] = {"Select City", "Kargil", "Leh"};
    String s19[] = {"Select City", "Balaghat", "Barwani", "Betul", "Bharhut", "Bhind", "Bhojpur", "Bhopal", "Burhanpur", "Chhatarpur", "Chhindwara", "Damoh", "Datia", "Dewas", "Dhar", "Dr. Ambedkar Nagar (Mhow)", "Guna", "Gwalior", "Hoshangabad", "Indore", "Itarsi", "Jabalpur", "Jhabua", "Khajuraho", "Khandwa", "Khargone", "Maheshwar", "Mandla", "Mandsaur", "Morena", "Murwara", "Narsimhapur", "Narsinghgarh", "Narwar", "Neemuch", "Nowgong", "Orchha", "Panna", "Raisen", "Rajgarh", "Ratlam", "Rewa", "Sagar", "Sarangpur", "Satna", "Sehore", "Seoni", "Shahdol", "Shajapur", "Sheopur", "Shivpuri", "Ujjain", "Vidisha"};
    String s20[] = {"Select City", "Ahmadnagar", "Akola", "Amravati", "Aurangabad", "Bhandara", "Bhusawal", "Bid", "Buldhana", "Chandrapur", "Daulatabad", "Dhule", "Jalgaon", "Kalyan", "Karli", "Kolhapur", "Mahabaleshwar", "Malegaon", "Matheran", "Mumbai", "Nagpur", "NandedNashik", "Osmanabad", "Pandharpur", "Parbhani", "Pune", "Ratnagiri", "Sangli", "Satara", "Sevagram", "Solapur", "Thane", "Ulhasnagar", "Vasai-Virar", "Wardha", "Yavatmal"};
    String s21[] = {"Select City", "Imphal"};
    String s22[] = {"Select City", "Cherrapunji", "Shillong"};
    String s23[] = {"Select City", "Aizawl", "Lunglei"};
    String s24[] = {"Select City", "Kohima", "Mon", "Phek", "Wokha", "Zunheboto"};
    String s25[] = {"Select City", "Balangir", "Baleshwar", "Baripada", "Bhubaneshwar", "Brahmapur", "Cuttack", "Dhenkanal ,Kendujhar", "Konark", "Koraput", "Paradip ,Phulabani", "Puri", "Sambalpur", "Udayagiri"};
    String s26[] = {"Select City", "Karaikal", "Mahe", "Puducherry", "Yanam"};
    String s27[] = {"Select City", "Amritsar", "Batala", "Chandigarh", "Faridkot", "Firozpur", "Gurdaspur", "Hoshiarpur", "Jalandhar", "Kapurthala", "Ludhiana", "Nabha", "Patiala", "Rupnagar", "Sangrur"};
    String s28[] = {"Select City", "Abu", "Ajmer", "Alwar", "Amer", "Barmer", "Beawar", "Bharatpur", "Bhilwara", "Bikaner", "Bundi", "Chittaurgarh", "Churu", "Dhaulpur", "Dungarpur", "Ganganagar", "Hanumangarh", "Jaipur", "Jaisalmer", "Jalor", "Jhalawar", "Jhunjhunu", "Jodhpur", "Kishangarh", "Kota", "Merta", "Nagaur", "Nathdwara", "Pali", "Phalodi", "Pushkar", "Sawai", "Madhopur", "Shahpura", "Sikar", "Sirohi", "Tonk", "Udaipur"};
    String s29[] = {"Select City", "Gangtok", "Gyalshing", "Lachung", "Mangan"};
    String s30[] = {"Select City", "Arcot", "Chengalpattu", "Chennai", "Chidambaram", "Coimbatore", "Cuddalore", "Dharmapuri", "Dindigul", "Erode", "Kanchipuram", "Kanniyakumari", "Kodaikanal", "Kumbakonam", "Madurai", "Mamallapuram", "Nagappattinam", "Nagercoil", "Palayamkottai", "Pudukkottai", "Rajapalayam", "Ramanathapuram", "Salem", "Thanjavur", "Tiruchchirappalli", "Tirunelveli", "Tiruppur", "Thoothukudi", "Udhagamandalam", "Vellore"};
    String s31[] = {"Select City", "Hyderabad", "Karimnagar", "Khammam", "Mahbubnagar", "Nizamabad", "Sangareddi", "Warangal"};
    String s32[] = {"Select City", "Arartala"};
    String s33[] = {"Select City", "Agra", "Aligarh", "Amroha", "Ayodhya", "Azamgarh", "Bahraich", "Ballia", "Banda", "Bara Banki", "Bareilly", "Basti", "Bijnor", "Bithur", "Budaun", "Bulandshahr", "Deoria", "Etah", "Etawah", "Faizabad", "Farrukhabad-cum-Fatehgarh", "Fatehpur", "Fatehpur Sikri", "Ghaziabad", "Ghazipur", "Gonda", "Gorakhpur", "Hamirpur", "Hardoi", "Hathras", "Jalaun", "Jaunpur", "Jhansi", "Kannauj", "Kanpur", "Lakhimpur", "Lalitpur", "Lucknow", "Mainpuri", "Mathura", "Meerut", "Mirzapur-Vindhyachal", "Moradabad", "Muzaffarnagar", "Partapgarh", "Pilibhit", "Prayagraj", "Rae Bareli", "Rampur", "Saharanpur", "Sambhal", "Shahjahanpur", "Sitapur", "Sultanpur", "Tehri", "Varanasi"};
    String s34[] = {"Select City", "Almora", "Dehra Dun", "Haridwar", "Mussoorie", "Nainital", "Pithoragarh"};
    String s35[] = {"Select City", "Alipore", "Alipur", "Duar", "Asansol", "Baharampur", "Bally", "Balurghat", "Bankura", "Baranagar", "Barasat", "Barrackpore", "Basirhat", "Bhatpara", "Bishnupur", "Budge", "Budge", "Burdwan", "Chandernagore", "Darjeeling", "Diamond", "Harbour", "Dum", "Dum", "Durgapur", "Halisahar", "Haora", "Hugli", "Ingraj", "Bazar", "Jalpaiguri", "Kalimpong", "Kamarhati", "Kanchrapara", "Kharagpur", "Cooch", "Behar", "Kolkata", "Krishnanagar", "Malda", "Midnapore", "Murshidabad", "Nabadwip", "Palashi", "Panihati", "Purulia", "Raiganj", "Santipur", "Shantiniketan", "Shrirampur", "Siliguri", "Siuri", "Tamluk", "Titagarh"};

    Spinner spStates, spCities;
    HashMap<String, ArrayList<String>> hmCities = new HashMap<>();
    EditText etName, etEmail, etPassword;
    RadioGroup rgGender;
    RadioButton rbMale, rbFemale;
    String userName="", userEmail="", userGender="", userPassword="", selectedState="", selectedCity="";
    Button btnRegister;
    FirebaseAuth fbAuth;
    FirebaseFirestore fbStore;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        spStates = findViewById(R.id.sp_states);
        spCities = findViewById(R.id.sp_cities);
        etName = findViewById(R.id.et_name);
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        rgGender = findViewById(R.id.rg_gender);
        rbMale = findViewById(R.id.rb_gender_male);
        rbFemale = findViewById(R.id.rb_gender_female);
        btnRegister = findViewById(R.id.btn_register);
        fbAuth = FirebaseAuth.getInstance();
        fbStore = FirebaseFirestore.getInstance();

        spStates.setAdapter(new ArrayAdapter<>(RegistrationActivity.this, android.R.layout.simple_list_item_1, states));
        spCities.setAdapter(new ArrayAdapter<>(RegistrationActivity.this, android.R.layout.simple_list_item_1, city));

//        etName.setText("Harsh");
//        etEmail.setText("harsh@gmail.com");
//        etPassword.setText("harsh123");
//        rbMale.setChecked(true);
//        spStates.setSelection(2);

        populateHashMap();

        spStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedState = spStates.getSelectedItem().toString();
                try{
                    if(position!=0){
                        spCities.setAdapter(new ArrayAdapter<String>(RegistrationActivity.this, android.R.layout.simple_list_item_1, hmCities.get(states[position])));
                    }else{
                        spCities.setAdapter(new ArrayAdapter<>(RegistrationActivity.this, android.R.layout.simple_list_item_1, city));
                    }
                } catch (Exception e) {

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        spCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedCity = spCities.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        rgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == rbMale.getId()){
                    rbMale.setTextColor(getResources().getColor(R.color.light_blue));
                    rbFemale.setTextColor(getResources().getColor(R.color.main_opposite));
                    userGender = "male";
                }else if(checkedId == rbFemale.getId()){
                    rbFemale.setTextColor(getResources().getColor(R.color.light_blue));
                    rbMale.setTextColor(getResources().getColor(R.color.main_opposite));
                    userGender = "female";
                }
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateInputs()){
                    Toast.makeText(RegistrationActivity.this, "Please provide all details correctly!", Toast.LENGTH_SHORT).show();
                }else{
                    try{
                        fbAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    if(fbAuth.getCurrentUser()!=null){
                                        try{
                                            HashMap<String, String> hmUserDetails = new HashMap<>();
                                            hmUserDetails.put("uid", fbAuth.getUid());
                                            hmUserDetails.put("name", userName);
                                            hmUserDetails.put("email", userEmail);
                                            hmUserDetails.put("gender", userGender);
                                            hmUserDetails.put("state", selectedState);
                                            hmUserDetails.put("city", selectedCity);
                                            hmUserDetails.put("hasprofile", "false");
                                            hmUserDetails.put("profilerotation", "0");
                                            fbStore.collection("people").document(fbAuth.getUid()).set(hmUserDetails).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(RegistrationActivity.this, "Registration Successful!", Toast.LENGTH_SHORT).show();
                                                    navigateToMainActivity();
                                                }
                                            });
                                        } catch (Exception e) {

                                        }
                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if(e.toString().contains("Collision")){
                                    Toast.makeText(RegistrationActivity.this, "E-mail already in use!", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(RegistrationActivity.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    } catch (Exception e) {
                    }
                }
            }
        });

    }
    public void populateHashMap(){
        try{
            hmCities.put(states[1], new ArrayList<String>(Arrays.asList(s0)));
            hmCities.put(states[2], new ArrayList<String>(Arrays.asList(s1)));
            hmCities.put(states[3], new ArrayList<String>(Arrays.asList(s2)));
            hmCities.put(states[4], new ArrayList<String>(Arrays.asList(s3)));
            hmCities.put(states[5], new ArrayList<String>(Arrays.asList(s4)));
            hmCities.put(states[6], new ArrayList<String>(Arrays.asList(s5)));
            hmCities.put(states[7], new ArrayList<String>(Arrays.asList(s6)));
            hmCities.put(states[8], new ArrayList<String>(Arrays.asList(s7)));
            hmCities.put(states[9], new ArrayList<String>(Arrays.asList(s8)));
            hmCities.put(states[10], new ArrayList<String>(Arrays.asList(s9)));
            hmCities.put(states[11], new ArrayList<String>(Arrays.asList(s10)));
            hmCities.put(states[12], new ArrayList<String>(Arrays.asList(s11)));
            hmCities.put(states[13], new ArrayList<String>(Arrays.asList(s12)));
            hmCities.put(states[14], new ArrayList<String>(Arrays.asList(s13)));
            hmCities.put(states[15], new ArrayList<String>(Arrays.asList(s14)));
            hmCities.put(states[16], new ArrayList<String>(Arrays.asList(s15)));
            hmCities.put(states[17], new ArrayList<String>(Arrays.asList(s16)));
            hmCities.put(states[18], new ArrayList<String>(Arrays.asList(s17)));
            hmCities.put(states[19], new ArrayList<String>(Arrays.asList(s18)));
            hmCities.put(states[20], new ArrayList<String>(Arrays.asList(s19)));
            hmCities.put(states[21], new ArrayList<String>(Arrays.asList(s20)));
            hmCities.put(states[22], new ArrayList<String>(Arrays.asList(s21)));
            hmCities.put(states[23], new ArrayList<String>(Arrays.asList(s22)));
            hmCities.put(states[24], new ArrayList<String>(Arrays.asList(s23)));
            hmCities.put(states[25], new ArrayList<String>(Arrays.asList(s24)));
            hmCities.put(states[26], new ArrayList<String>(Arrays.asList(s25)));
            hmCities.put(states[27], new ArrayList<String>(Arrays.asList(s26)));
            hmCities.put(states[28], new ArrayList<String>(Arrays.asList(s27)));
            hmCities.put(states[29], new ArrayList<String>(Arrays.asList(s28)));
            hmCities.put(states[30], new ArrayList<String>(Arrays.asList(s29)));
            hmCities.put(states[31], new ArrayList<String>(Arrays.asList(s30)));
            hmCities.put(states[32], new ArrayList<String>(Arrays.asList(s31)));
            hmCities.put(states[33], new ArrayList<String>(Arrays.asList(s32)));
            hmCities.put(states[34], new ArrayList<String>(Arrays.asList(s33)));
            hmCities.put(states[35], new ArrayList<String>(Arrays.asList(s34)));
            hmCities.put(states[36], new ArrayList<String>(Arrays.asList(s35)));
        } catch (Exception e) {

        }
    }

    public Boolean validateInputs(){
        try{
            if(!etName.getText().toString().trim().isEmpty() && !etEmail.getText().toString().trim().isEmpty() && !etPassword.getText().toString().trim().isEmpty()){
                if(etPassword.length()<8){
                    Toast.makeText(this, "Enter appropriate alphanumeric password with at-least 8 characters!", Toast.LENGTH_SHORT).show();
                }
                userName = etName.getText().toString().trim();
                userEmail = etEmail.getText().toString().trim();
                userPassword = etPassword.getText().toString().trim();
                if(rbMale.isChecked() || rbFemale.isChecked()){
                    if(!selectedState.equals("Select State")){
                        if(!selectedCity.equals("Select a state first!") && !selectedCity.equals("Select City")){
                            return true;
                        }
                    }
                }
            }
        } catch (Exception e) {
        }
        return false;
    }

    public void redirectToLogin(View view) {
        try{
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            finish();
        } catch (Exception e) {
        }
    }
    public void navigateToMainActivity(){
        try{
            startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
            finish();
        } catch (Exception e) {

        }
    }
}