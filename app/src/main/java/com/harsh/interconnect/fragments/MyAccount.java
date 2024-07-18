package com.harsh.interconnect.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirestoreKt;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.harsh.interconnect.LoginActivity;
import com.harsh.interconnect.R;
import com.harsh.interconnect.RegistrationActivity;
import com.harsh.interconnect.globaldata.GlobalData;
import com.harsh.interconnect.utils.ProfileEditorActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MyAccount extends Fragment {

    String states[] = {"Change State", "Andaman and Nicobar", "Andhra Pradesh", "Arunachal Pradesh", "Assam", "Bihar", "Chandigarh", "Chhattisgarh", "Dadranagar Haveli", "Diu and Daman", "Delhi", "Goa", "Gujarat", "Haryana", "Himachal Pradesh", "Jammu and Kashmir", "Jharkhand", "Karnataka", "Kerala", "Ladakh", "Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", "Nagaland", "Odisha", "Puducherry", "Punjab", "Rajasthan", "Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttar Pradesh", "Uttarakhand", "West Bengal"};
    String city[] = {"Select a state first!"};

    String s0[] = {"Change City", "Port Blair"};
    String s1[] = {"Change City", "Adoni", "Amaravati", "Anantapur", "Chandragiri", "Chittoor", "Dowlaiswaram", "Eluru", "Guntur", "Kadapa", "Kakinada", "Kurnool", "Machilipatnam", "Nagarjunakonda", "Rajahmundry", "Srikakulam", "Tirupati", "Vijayawada", "Visakhapatnam", "Vizianagaram", "Yemmiganur"};
    String s2[] = {"Change City", "Itanagar"};
    String s3[] = {"Change City", "Dhuburi", "Dibrugarh", "Dispur", "Guwahati", "Jorhat", "Nagaon", "Sivasagar", "Silchar", "Tezpur", "Tinsukia"};
    String s4[] = {"Change City", "Ara", "Barauni", "Begusarai", "Bettiah", "Bhagalpur", "Bihar", "Sharif", "Bodh", "Gaya", "Buxar", "Chapra", "Darbhanga", "Dehri", "Dinapur", "Nizamat", "Gaya", "Hajipur", "Jamalpur", "Katihar", "Madhubani", "Motihari", "Munger", "Muzaffarpur", "Patna", "Purnia", "Pusa", "Saharsa", "Samastipur", "Sasaram", "Sitamarhi", "Siwan"};
    String s5[] = {"Change City", "Chandigarh"};
    String s6[] = {"Change City", "Ambikapur", "Bhilai", "Bilaspur", "Dhamtari", "Durg", "Jagdalpur", "Raipur", "Rajnandgaon"};
    String s7[] = {"Change City", "Dadranagar Haveli"};
    String s8[] = {"Change City", "Diu and Daman"};
    String s9[] = {"Change City", "Delhi", "New Delhi"};
    String s10[] = {"Change City", "Madgaon", "Panaji"};
    String s11[] = {"Change City", "Ahmedabad", "Amreli", "Bharuch", "Bhavnagar", "Bhuj", "Dwarka", "Gandhinagar", "Godhra", "Jamnagar", "Junagadh", "Kandla", "Khambhat", "Kheda", "Mahesana", "Morbi", "Nadiad", "Navsari", "Okha", "Palanpur", "Patan", "Porbandar", "Rajkot", "Surat", "Surendranagar", "Valsad", "Veraval"};
    String s12[] = {"Change City", "Ambala", "Bhiwani", "Chandigarh", "Faridabad", "Firozpur", "Jhirka", "Gurugram", "Hansi", "Hisar", "Jind", "Kaithal", "Karnal", "Kurukshetra", "Panipat", "Pehowa", "Rewari", "Rohtak", "Sirsa", "Sonipat"};
    String s13[] = {"Change City", "Bilaspur", "Chamba", "Dalhousie", "Dharmshala", "Hamirpur", "Kangra", "Kullu", "Mandi", "Nahan", "Shimla", "Una"};
    String s14[] = {"Change City", "Anantnag", "Baramula", "Doda", "Gulmarg", "Jammu", "Kathua", "Punch", "Rajouri", "Srinagar", "Udhampur"};
    String s15[] = {"Change City", "Bokaro", "Chaibasa", "Deoghar", "Dhanbad", "Dumka", "Giridih", "Hazaribag", "Jamshedpur", "Jharia", "Rajmahal", "Ranchi", "Saraikela"};
    String s16[] = {"Change City", "Badami", "Ballari", "Bengaluru", "Belagavi", "Bhadravati", "Bidar", "Chikkamagaluru", "Chitradurga", "Davangere", "Halebid", "Hassan", "Hubballi-Dharwad", "Kalaburagi", "Kolar", "Madikeri", "Mandya", "Mangaluru", "Mysuru", "Raichur", "Shivamogga", "Shravanabelagola", "Shrirangapattana", "Tumakuru", "Vijayapura" };
    String s17[] = {"Change City", "Alappuzha", "VatakaraIdukki", "Kannur", "Kochi", "KollamKottayam", "Kozhikode", "Mattancheri", "Palakkad", "Thalassery", "Thiruvananthapuram", "Thrissur" };
    String s18[] = {"Change City", "Kargil", "Leh"};
    String s19[] = {"Change City", "Balaghat", "Barwani", "Betul", "Bharhut", "Bhind", "Bhojpur", "Bhopal", "Burhanpur", "Chhatarpur", "Chhindwara", "Damoh", "Datia", "Dewas", "Dhar", "Dr. Ambedkar Nagar (Mhow)", "Guna", "Gwalior", "Hoshangabad", "Indore", "Itarsi", "Jabalpur", "Jhabua", "Khajuraho", "Khandwa", "Khargone", "Maheshwar", "Mandla", "Mandsaur", "Morena", "Murwara", "Narsimhapur", "Narsinghgarh", "Narwar", "Neemuch", "Nowgong", "Orchha", "Panna", "Raisen", "Rajgarh", "Ratlam", "Rewa", "Sagar", "Sarangpur", "Satna", "Sehore", "Seoni", "Shahdol", "Shajapur", "Sheopur", "Shivpuri", "Ujjain", "Vidisha"};
    String s20[] = {"Change City", "Ahmadnagar", "Akola", "Amravati", "Aurangabad", "Bhandara", "Bhusawal", "Bid", "Buldhana", "Chandrapur", "Daulatabad", "Dhule", "Jalgaon", "Kalyan", "Karli", "Kolhapur", "Mahabaleshwar", "Malegaon", "Matheran", "Mumbai", "Nagpur", "NandedNashik", "Osmanabad", "Pandharpur", "Parbhani", "Pune", "Ratnagiri", "Sangli", "Satara", "Sevagram", "Solapur", "Thane", "Ulhasnagar", "Vasai-Virar", "Wardha", "Yavatmal"};
    String s21[] = {"Change City", "Imphal"};
    String s22[] = {"Change City", "Cherrapunji", "Shillong"};
    String s23[] = {"Change City", "Aizawl", "Lunglei"};
    String s24[] = {"Change City", "Kohima", "Mon", "Phek", "Wokha", "Zunheboto"};
    String s25[] = {"Change City", "Balangir", "Baleshwar", "Baripada", "Bhubaneshwar", "Brahmapur", "Cuttack", "Dhenkanal ,Kendujhar", "Konark", "Koraput", "Paradip ,Phulabani", "Puri", "Sambalpur", "Udayagiri"};
    String s26[] = {"Change City", "Karaikal", "Mahe", "Puducherry", "Yanam"};
    String s27[] = {"Change City", "Amritsar", "Batala", "Chandigarh", "Faridkot", "Firozpur", "Gurdaspur", "Hoshiarpur", "Jalandhar", "Kapurthala", "Ludhiana", "Nabha", "Patiala", "Rupnagar", "Sangrur"};
    String s28[] = {"Change City", "Abu", "Ajmer", "Alwar", "Amer", "Barmer", "Beawar", "Bharatpur", "Bhilwara", "Bikaner", "Bundi", "Chittaurgarh", "Churu", "Dhaulpur", "Dungarpur", "Ganganagar", "Hanumangarh", "Jaipur", "Jaisalmer", "Jalor", "Jhalawar", "Jhunjhunu", "Jodhpur", "Kishangarh", "Kota", "Merta", "Nagaur", "Nathdwara", "Pali", "Phalodi", "Pushkar", "Sawai", "Madhopur", "Shahpura", "Sikar", "Sirohi", "Tonk", "Udaipur"};
    String s29[] = {"Change City", "Gangtok", "Gyalshing", "Lachung", "Mangan"};
    String s30[] = {"Change City", "Arcot", "Chengalpattu", "Chennai", "Chidambaram", "Coimbatore", "Cuddalore", "Dharmapuri", "Dindigul", "Erode", "Kanchipuram", "Kanniyakumari", "Kodaikanal", "Kumbakonam", "Madurai", "Mamallapuram", "Nagappattinam", "Nagercoil", "Palayamkottai", "Pudukkottai", "Rajapalayam", "Ramanathapuram", "Salem", "Thanjavur", "Tiruchchirappalli", "Tirunelveli", "Tiruppur", "Thoothukudi", "Udhagamandalam", "Vellore"};
    String s31[] = {"Change City", "Hyderabad", "Karimnagar", "Khammam", "Mahbubnagar", "Nizamabad", "Sangareddi", "Warangal"};
    String s32[] = {"Change City", "Arartala"};
    String s33[] = {"Change City", "Agra", "Aligarh", "Amroha", "Ayodhya", "Azamgarh", "Bahraich", "Ballia", "Banda", "Bara Banki", "Bareilly", "Basti", "Bijnor", "Bithur", "Budaun", "Bulandshahr", "Deoria", "Etah", "Etawah", "Faizabad", "Farrukhabad-cum-Fatehgarh", "Fatehpur", "Fatehpur Sikri", "Ghaziabad", "Ghazipur", "Gonda", "Gorakhpur", "Hamirpur", "Hardoi", "Hathras", "Jalaun", "Jaunpur", "Jhansi", "Kannauj", "Kanpur", "Lakhimpur", "Lalitpur", "Lucknow", "Mainpuri", "Mathura", "Meerut", "Mirzapur-Vindhyachal", "Moradabad", "Muzaffarnagar", "Partapgarh", "Pilibhit", "Prayagraj", "Rae Bareli", "Rampur", "Saharanpur", "Sambhal", "Shahjahanpur", "Sitapur", "Sultanpur", "Tehri", "Varanasi"};
    String s34[] = {"Change City", "Almora", "Dehra Dun", "Haridwar", "Mussoorie", "Nainital", "Pithoragarh"};
    String s35[] = {"Change City", "Alipore", "Alipur", "Duar", "Asansol", "Baharampur", "Bally", "Balurghat", "Bankura", "Baranagar", "Barasat", "Barrackpore", "Basirhat", "Bhatpara", "Bishnupur", "Budge", "Budge", "Burdwan", "Chandernagore", "Darjeeling", "Diamond", "Harbour", "Dum", "Dum", "Durgapur", "Halisahar", "Haora", "Hugli", "Ingraj", "Bazar", "Jalpaiguri", "Kalimpong", "Kamarhati", "Kanchrapara", "Kharagpur", "Cooch", "Behar", "Kolkata", "Krishnanagar", "Malda", "Midnapore", "Murshidabad", "Nabadwip", "Palashi", "Panihati", "Purulia", "Raiganj", "Santipur", "Shantiniketan", "Shrirampur", "Siliguri", "Siuri", "Tamluk", "Titagarh"};


    HashMap<String, ArrayList<String>> hmCities = new HashMap<>();
    ImageView ivProfile;
    Button btnLogout, btnSaveChanges, btnRemoveProfile;
    Spinner spStates, spCities;
    EditText etName;
    TextView tvCurrentState, tvCurrentCity;
    FirebaseFirestore fbStore;
    StorageReference fbDatabase;
    String uName = "", uState="", uCity="", selectedState = "", selectedCity="";
    public static Boolean nameChanged=false, stateChanged=false, cityChange=false, hasProfile=false;

    public static Float profileRotation=0.f;
    public static Uri profileImageUri;

    public MyAccount() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragMyAccount = inflater.inflate(R.layout.fragment_my_account, container, false);
        try{
            etName = fragMyAccount.findViewById(R.id.et_my_account_name);
            btnLogout = fragMyAccount.findViewById(R.id.btn_logout);
            btnRemoveProfile = fragMyAccount.findViewById(R.id.btn_remove_profile);
            btnSaveChanges = fragMyAccount.findViewById(R.id.btn_save_changes);
            ivProfile = fragMyAccount.findViewById(R.id.iv_profile);
            spStates = fragMyAccount.findViewById(R.id.sp_my_account_states);
            tvCurrentState = fragMyAccount.findViewById(R.id.tv_my_account_selected_state);
            spCities = fragMyAccount.findViewById(R.id.sp_my_account_cities);
            tvCurrentCity = fragMyAccount.findViewById(R.id.tv_my_account_selected_city);
            fbStore = FirebaseFirestore.getInstance();
            fbDatabase = FirebaseStorage.getInstance().getReference("images/profile/"+GlobalData.fbAuth.getUid());

            updateFields();

            populateHashMap();

            spStates.setAdapter(new ArrayAdapter<>(fragMyAccount.getContext(), android.R.layout.simple_list_item_1, states));
            spCities.setAdapter(new ArrayAdapter<>(fragMyAccount.getContext(), android.R.layout.simple_list_item_1, city));

            ivProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent inImageSelector = new Intent(Intent.ACTION_PICK);
                    inImageSelector.setType("image/*");
                    startActivityForResult(inImageSelector, 2004);
                }
            });
            btnRemoveProfile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
                    AlertDialog ad = adb.create();
                    adb.setTitle("Are your sure?");
                    adb.setMessage("Do you want to remove the profile picture?");
                    adb.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            removeProfileImage();
                        }
                    });
                    adb.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ad.dismiss();
                        }
                    });
                    adb.show();
                }
            });

            spStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedState = spStates.getSelectedItem().toString();
                    try{
                        if(position!=0){
                            spCities.setAdapter(new ArrayAdapter<>(fragMyAccount.getContext(), android.R.layout.simple_list_item_1, hmCities.get(states[position])));
                        }else{
                            spCities.setAdapter(new ArrayAdapter<>(fragMyAccount.getContext(), android.R.layout.simple_list_item_1, city));
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

            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseAuth fbAuth = FirebaseAuth.getInstance();
                    fbAuth.signOut();
                    Toast.makeText(getContext(), "Signed out!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(), LoginActivity.class));
                    getActivity().finish();
                }
            });
            btnSaveChanges.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HashMap<String, String> hmUserDetails = new HashMap<>();
                    hmUserDetails.put("name", etName.getText().toString().trim());
                    hmUserDetails.put("state", selectedState);
                    hmUserDetails.put("city", selectedCity);
                    if(nameChanged() && stateChanged()){
                        fbStore.collection("people").document(GlobalData.fbAuth.getUid()).update("name", etName.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                nameChanged = true;
                                if(stateChanged && cityChange){
                                    Toast.makeText(getContext(), "Name, State and City are changed!", Toast.LENGTH_SHORT).show();
                                    updateFields();
                                }
                            }
                        });
                        fbStore.collection("people").document(GlobalData.fbAuth.getUid()).update("state", spStates.getSelectedItem().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                stateChanged = true;
                                if(nameChanged && cityChange){
                                    Toast.makeText(getContext(), "Name, State and City are changed!", Toast.LENGTH_SHORT).show();
                                    updateFields();
                                }
                            }
                        });
                        fbStore.collection("people").document(GlobalData.fbAuth.getUid()).update("city", spCities.getSelectedItem().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                cityChange = true;
                                if(nameChanged && stateChanged()){
                                    Toast.makeText(getContext(), "Name, State and City are changed!", Toast.LENGTH_SHORT).show();
                                    updateFields();
                                }
                            }
                        });
                    }
                    if(nameChanged()){
                        fbStore.collection("people").document(GlobalData.fbAuth.getUid()).update("name", etName.getText().toString().trim()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getContext(), "Name changed!", Toast.LENGTH_SHORT).show();
                                updateFields();
                            }
                        });
                    }
                    if(stateChanged()){
                        fbStore.collection("people").document(GlobalData.fbAuth.getUid()).update("state", spStates.getSelectedItem().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                fbStore.collection("people").document(GlobalData.fbAuth.getUid()).update("city", spCities.getSelectedItem().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(getContext(), "State and City are changed!", Toast.LENGTH_SHORT).show();
                                        updateFields();
                                    }
                                });
                            }
                        });
                    }
                    if(!nameChanged() && !stateChanged()){
                        Toast.makeText(getContext(), "No changes are made!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (Exception e) {
            Log.d("dalle", e+"");
        }
        return fragMyAccount;
    }
    public Boolean nameChanged(){
        if(!etName.getText().toString().trim().equals(uName)){
            return true;
        }
        return false;
    }
    public Boolean stateChanged(){
        if(!spStates.getSelectedItem().toString().equals("Change State") && !spStates.getSelectedItem().toString().equals(uState)){
            if(!spCities.getSelectedItem().toString().equals("Select a state first!") && !spCities.getSelectedItem().toString().equals("Change City") && !spCities.getSelectedItem().toString().equals(uCity)){
                return true;
            }
        }
        return false;
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

    public void updateFields(){
        fbStore.collection("people").document(GlobalData.fbAuth.getUid()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                try{
                    uName = value.getString("name");
                    etName.setText(uName);
                    uState = value.getString("state");
                    uCity = value.getString("city");
                    tvCurrentState.setText("Current state: "+uState);
                    hasProfile = Boolean.valueOf(value.getString("hasprofile"));
                    tvCurrentCity.setText("Current city: "+uCity);
                    if(hasProfile){
                        profileRotation = Float.parseFloat(value.getString("profilerotation"));
                        ivProfile.setImageResource(R.drawable.ic_loading_fg);
                        btnRemoveProfile.setVisibility(View.VISIBLE);
                        fbDatabase.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull Task<Uri> task) {
                               try{
                                   if(task.isSuccessful()){
                                       Picasso.get().load(task.getResult()).into(ivProfile);
                                       if(profileRotation>0.f){
                                           ivProfile.setRotation(profileRotation);
                                       }
                                   }
                               } catch (Exception e) {

                               }
                            }
                        });
//                        fbDatabase.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
//                            @Override
//                            public void onSuccess(Uri uri) {
//                            }
//                        });
                    }
                } catch (Exception e) {}
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2004 && data!=null){
            ivProfile.setImageDrawable(getResources().getDrawable(R.drawable.ic_loading_fg));
            profileImageUri = data.getData();
            fbStore.collection("people").document(GlobalData.fbAuth.getUid()).update("profilerotation", "0.0");

            fbStore.collection("people").document(GlobalData.fbAuth.getUid()).update("hasprofile","true").addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    uploadProfileImage(data.getData());
                }
            });
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        updateFields();
    }

    public void uploadProfileImage(Uri profileImageUri){
        fbDatabase.putFile(profileImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Intent inProfileEditor = new Intent(getContext(), ProfileEditorActivity.class);
                Bundle bn = new Bundle();
                bn.putString("profileUri", String.valueOf(profileImageUri));
                inProfileEditor.putExtra("bundle", bn);
                startActivity(inProfileEditor);
                Toast.makeText(getContext(), "Select profile rotation if required and click on set profile!", Toast.LENGTH_LONG).show();
                updateFields();
            }
        });
    }
    public void removeProfileImage(){
        try{
            ivProfile.setRotation(0.f);
            ivProfile.setImageDrawable(getResources().getDrawable(R.drawable.ic_loading_fg));
            fbStore.collection("people").document(GlobalData.fbAuth.getUid()).update("hasprofile", "false").addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    fbDatabase.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getContext(), "Profile removed!", Toast.LENGTH_SHORT).show();
                            ivProfile.setImageResource(R.drawable.ic_add_profile);
                            ivProfile.setRotation(0.f);
                            btnRemoveProfile.setVisibility(View.GONE);
                        }
                    });
                }
            });
        } catch (Exception e) {}
    }
}