package com.example.myapplication.util;

/**
 * Created by aj1083 on 8/30/2016.
 */
public class ApplicationHolder {
    public static String userNames[]={"Anand","Naga babu","Suresh","Raghu","Avinash","Arun","swathi","shruthi","Shekar","Kajal"};
    public static String userPhone[]={"9533320096","9498663556","9856237418","9884812113","9666100456","7812456395","9533320095","9865321245","8880123564","9645689745"};
    public static String userState[]={"Andhra Pradesh","Telangana","Assam","Tamilnadu","Bhihar","Andhra Pradesh","Telangana","Assam","Tamilnadu","Bhihar"};
    public static String userCity[]={"Vijayawada","Hyderabad","Guwahati","Chennai","Patna","Vijayawada","Hyderabad","Guwahati","Chennai","Patna"};
    public static String userPincode[]={"521213","500045","500456","567435","523432","521213","500045","500456","567435","523432"};
    public static String userBloogGroup[]={"O+","A","AB+","B+","AB-","O-","A-","B-","O+","AB+"};

    public static String base_url="http://websitesdev.in/jjyothi/index.php/";
    public static String login_url="Userlogin/login?";
    public static String registration_url="Userlogin/userRegistration?";
    public static String showProfile_url="Profile/showProfile?";
    public static String updateProfile_url= "Profile/updateDonorprofile?";
    public static String addRequest_url="Bloodrequest/addRequest?";
    public static String editRequest_url="Bloodrequest/editrequestformobile?";
    public static String show_allRequests_url="Bloodrequest/showallrequest";
    public static String notification_url="Notifications/showallnotification";
    public static String show_my_Requests_url="Bloodrequest/showmyrequest?";
    public static String notification_count_url="Notifications/notificationcount";
    public static String show_all_donors_url="Search/donors";
    public static String search_donors_url="Search/searchdonor?";
    public static String contact_us_url="ContactUs/contactform?";
    public static String accept_url="Notifications/acceptNotifications?";


    public static String TOKEN_ID="token_id";
    public static String MAC_ID="mac_id";
    public static String USERNAME="username";
    public static String PASSWORD="password";
    public static String DONAR_NAME="donar_name";
    public static String EMAILID="emailid";
    public static String DOB="dob";
    public static String GENDER="gender";
    public static String BLOODGROUP="bloodgroup";
    public static String HOW_OFTEN="how_often";
    public static String WEIGHT="weight";
    public static String BDD="blooddonateddate";
    public static String RES_PHONE="res_phno";
    public static String ADDRESS="address1";
    public static String CITY="city";
    public static String MOBILENO="mob_no";
    public static String EMAIL="email";
    public static String BLOOD="blood";



   /* Registration

    http://websitesdev.in/jjyothi/index.php/Userlogin/userRegistration?
    donor_name=&emailid=&password=&dob=&gender=&bloodgroup=&how_often=&weight=&blooddonated
    date=&res_phno=&address1=&city_name=&mob_no=

    Login

    http://websitesdev.in/jjyothi/index.php/Userlogin/login?username=&password=

    Show User Profile:

    http://websitesdev.in/jjyothi/index.php/Profile/showProfile?
    email=suresh.pegadapelli@gmail.com

    Update User Profile:

    http://websitesdev.in/jjyothi/index.php/Profile/updateDonorprofile?
    donor_name=&dob=&blood=&weight=&res_phno=&address1=&city_name=&mob_no=&userID=&image=

    Add Request:

    http://websitesdev.in/jjyothi/index.php/Bloodrequest/addRequest?
    pname=&Bgroup=&pAge=&needon=&units=&mNumber=&lNumber=&hName=&location=&paddress=&purpos
    e=&cName=&cEmail=

    Edit Request:

    http://websitesdev.in/jjyothi/index.php/Bloodrequest/editrequestformobile?
    pname=&Bgroup=&pAge=&needon=&units=&mNumber=&lNumber=&hName=&location=&paddress=&purpos
    e=&cName=&cEmail=&requestId=

    Show All Request

    http://websitesdev.in/jjyothi/index.php/Bloodrequest/showallrequest*/


}
