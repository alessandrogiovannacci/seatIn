package sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Ale
 */
public class SqlDMLManager {
    
    //inner enum containing all SELECT QUERIES
    public enum SelectQuery {
        
        NUMBER_OF_ADMIN("SELECT COUNT(*) FROM public.\"User\" WHERE public.\"User\".role = 'Administrator';"),
        USER_LOGIN("SELECT public.\"User\".email, public.\"User\".password FROM public.\"User\" WHERE public.\"User\".email = ?;"),
        STUDENT_LOGIN("SELECT public.\"Student\".email, public.\"Student\".password FROM public.\"Student\" WHERE public.\"Student\".email = ?"),
        USER_PROFILE_ACTIVATED("SELECT public.\"User\".profileactivated FROM public.\"User\" WHERE public.\"User\".email = ?"),
        STUDENT_PROFILE_ACTIVATED("SELECT public.\"Student\".profileactivated FROM public.\"Student\" WHERE public.\"Student\".email = ?"),
        USER_ACTIVATIONCODE("SELECT public.\"User\".activationcode FROM public.\"User\" WHERE public.\"User\".email = ?"),
        STUDENT_ACTIVATIONCODE("SELECT public.\"Student\".activationcode FROM public.\"Student\" WHERE public.\"Student\".email = ?"),
        USER_EMAIL("SELECT public.\"User\".email FROM public.\"User\" WHERE public.\"User\".email = ?"),
        STUDENT_EMAIL("SELECT public.\"Student\".email FROM public.\"Student\" WHERE public.\"Student\".email = ?"),
        USER_DATA("SELECT name,surname,department,email FROM public.\"User\" WHERE number = ?"),
        STUDENT_DATA("SELECT name,surname,degreecourse,careerstate,email FROM public.\"Student\" WHERE number = ?"),
        STUDENTS_NUMBERS("SELECT number FROM public.\"Student\""),
        USERS_NUMBERS("SELECT number FROM public.\"User\" WHERE role = ?"),
        COURSES_ID("SELECT id FROM public.\"Course\""),
        STUDYPLAN_ALREADY_REGISTERED("SELECT COUNT(*) FROM public.\"Studyplan\" WHERE  studentnumber = ?"),
        STUDENT_NUMBER("SELECT number FROM public.\"Student\" WHERE email = ?"),
        USER_NUMBER("SELECT number FROM public.\"User\" WHERE email = ?"),
        STUDENT_COURSES_IN_STUDY_PLAN("SELECT courseid FROM public.\"Studyplancontainedcourses\" WHERE studentnumber = ?"),
        COURSE_DATA("SELECT name,description FROM public.\"Course\" WHERE id = ?"),
        PROFESSORS_ID("SELECT usernumber FROM public.\"Userhandledcourses\" WHERE courseid = ?"),
        IS_STUDENT_SIGNED_TO_COURSE("SELECT COUNT(*) FROM public.\"Studentsignedcourses\" WHERE studentnumber = ? AND courseid = ?"),
        STUDENT_EMAIL_GETBYNUMBER("SELECT public.\"Student\".email FROM public.\"Student\" WHERE public.\"Student\".number = ?"),
        PROFESSOR_HANDLED_COURSES("SELECT courseid FROM public.\"Userhandledcourses\" WHERE usernumber = ?"),
        IS_PROFESSOR_SIGNED_TO_COURSE("SELECT COUNT(*) FROM public.\"Userhandledcourses\" WHERE usernumber = ? AND courseid = ?"),
        FOLDERS_NAME("SELECT name FROM public.\"Resource\" WHERE type = 'folder' AND sectionid = ?"),
        SECTIONS_ID("SELECT sectionid FROM public.\"Coursecontainedsection\" WHERE courseid = ?"),
        SECTION_NAME("SELECT name FROM public.\"Section\" WHERE id = ?"),
        SECTION_DATA("SELECT name,description FROM public.\"Section\" WHERE id = ?"),
        SUBSECTION_DATA("SELECT id,name FROM public.\"Section\" WHERE upsection = ?"),
        RESOURCES_DATA("SELECT id,name,type FROM public.\"Resource\" WHERE sectionid = ?"),
        STUDENT_AUTHATTEMPTS("SELECT authenticationattempts FROM public.\"Student\" WHERE email = ?"),
        USER_AUTHATTEMPTS("SELECT authenticationattempts FROM public.\"User\" WHERE email = ?"),
        SECTION_VISIBILITY("SELECT visibility FROM public.\"Section\" WHERE id = ?"),
        UPSECTION_ID("SELECT id from public.\"Section\" where upsection IS null");
               
        
        private String query;
        
        private SelectQuery(String q){
            this.query = q;
        }
        
        @Override
        public String toString(){
            return this.query;
        }
        
    }
    
    //inner enum containing all INSERT QUERIES
    public enum InsertQuery {
        
        INSERT_USER("INSERT INTO public.\"User\"(number,name,surname,role,department,email,password,activationcode,profileactivated,authenticationattempts) VALUES (?,?,?,?,?,?,?,?,?,?)"),
        INSERT_STUDENT("INSERT INTO public.\"Student\" (number,name,surname,degreecourse,careerstate,email,password,registrationyear,activationcode,profileactivated,authenticationattempts) VALUES (?,?,?,?,?,?,?,?,?,?,?)"),
        INSERT_COURSE("INSERT INTO public.\"Course\" (id,name,activationyear,degreecourse,description) VALUES (?,?,?,?,?)"),
        ASSIGN_COURSE_TO_STUDENT("INSERT INTO public.\"Studyplancontainedcourses\" (studentnumber,courseid) VALUES (?,?)"),
        ASSIGN_COURSE_TO_USER("INSERT INTO public.\"Userhandledcourses\" (usernumber,courseid) VALUES (?,?)"),
        REGISTER_STUDYPLAN("INSERT INTO public.\"Studyplan\" (studentnumber) VALUES(?)"),
        SIGN_STUDENT_TO_COURSE("INSERT INTO public.\"Studentsignedcourses\" (studentnumber,courseid) VALUES (?,?)"),
        INSERT_SECTION("INSERT INTO public.\"Section\" (id,name,description,visibility,upsection) VALUES (?,?,?,?,?)"),
        INSERT_SECTION_IN_COURSE("INSERT INTO public.\"Coursecontainedsection\" (courseid,sectionid) VALUES (?,?)"),
        INSERT_RESOURCE("INSERT INTO public.\"Resource\" (id,name,description,type,visibility,sectionid,folder) VALUES (?,?,?,?,?,?,?)");
        
        private String query;
        
        private InsertQuery(String q){
            this.query = q;
        }
        
        @Override
        public String toString(){
            return this.query;
        }
        
    }
    
    //inner enum containing all DELETE QUERIES
    public enum DeleteQuery {
        
        DELETE_SECTION("DELETE FROM public.\"Section\" WHERE id = ?");
        
        private String query;
        
        private DeleteQuery(String q){
            this.query = q;
        }
        
        @Override
        public String toString(){
            return this.query;
        }
        
    }
    
    //inner enum containing all UPDATE QUERIES
    public enum UpdateQuery{
    
        RESET_USER_PASSWORD("UPDATE public.\"User\" SET password = ? WHERE email = ?"),
        RESET_STUDENT_PASSWORD("UPDATE public.\"Student\" SET password = ? WHERE email = ?"),
        RESET_USER_ACTIVATIONCODE("UPDATE public.\"User\" SET activationcode = ? WHERE email = ?"),
        RESET_STUDENT_ACTIVATIONCODE("UPDATE public.\"Student\" SET activationcode = ? WHERE email = ?"),
        ACTIVATE_USER_PROFILE("UPDATE public.\"User\" SET profileactivated = true AND authenticationattempts = 0 WHERE email = ?"),
        ACTIVATE_STUDENT_PROFILE("UPDATE public.\"Student\" SET profileactivated = true AND authenticationattempts = 0 WHERE email = ?"),
        UPDATE_USER_NAME("UPDATE public.\"User\" SET name = ? WHERE number = ?"),
        UPDATE_USER_SURNAME("UPDATE public.\"User\" SET surname = ? WHERE number = ?"),
        UPDATE_USER_DEPARTMENT("UPDATE public.\"User\" SET department = ? WHERE number = ?"),
        UPDATE_USER_EMAIL("UPDATE public.\"User\" SET email = ? WHERE number = ?"),
        UPDATE_STUDENT_NAME("UPDATE public.\"Student\" SET name = ? WHERE number = ?"),
        UPDATE_STUDENT_SURNAME("UPDATE public.\"Student\" SET surname = ? WHERE number = ?"),
        UPDATE_STUDENT_DEGREECOURSE("UPDATE public.\"Student\" SET degreecourse = ? WHERE number = ?"),
        UPDATE_STUDENT_CAREERSTATE("UPDATE public.\"Student\" SET careerstate = ? WHERE number = ?"),
        UPDATE_STUDENT_EMAIL("UPDATE public.\"Student\" SET email = ? WHERE number = ?"),
        UPDATE_STUDENT_AUTHATTEMPTS("UPDATE public.\"Student\" SET authenticationattempts = ? WHERE email = ?"),
        UPDATE_USER_AUTHATTEMPTS("UPDATE public.\"User\" SET authenticationattempts = ? WHERE email = ?"),
        DEACTIVATE_USER_PROFILE("UPDATE public.\"User\" SET profileactivated = false WHERE email = ?"),
        DEACTIVATE_STUDENT_PROFILE("UPDATE public.\"Student\" SET profileactivated = false WHERE email = ?"),
        UPDATE_DOWNLOAD_NUMBER_AND_DATE("UPDATE public.\"Resource\" SET downloadnumber = downloadnumber +1, downloadtime = ? WHERE name = ?");
        
        
        private String query;
        
        private UpdateQuery(String q){
            this.query = q;
        }
        
        @Override
        public String toString(){
            return this.query;
        }
    
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    public void execute_sel_Query(Connection con, String sqlCode) throws SQLException{
        try(Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sqlCode);
            //System.out.println(sqlCode);
            while(rs.next()){
		int numCol = rs.getMetaData().getColumnCount();
		String row="";
		for(int i = 1;i <= numCol; i++){
                    int type=rs.getMetaData().getColumnType(i);
                    String cell="";
                    switch(type){
			case java.sql.Types.BIGINT:
			case java.sql.Types.INTEGER:
			case java.sql.Types.SMALLINT:
			case java.sql.Types.TINYINT:
			case java.sql.Types.NUMERIC:
				cell=cell+""+rs.getLong(i);
				break;
			case java.sql.Types.DATE:
				cell=cell+""+rs.getDate(i).toString();
				break;
			case java.sql.Types.VARCHAR:
			case java.sql.Types.CHAR:
				cell=cell+""+rs.getString(i);
				break;
                    }
                    row=row+"\t"+cell;
		}
		System.out.println(row);
            }
	}
	catch(SQLException e){}
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    public long viewAdminNumber(Connection con, String sqlCode) throws SQLException{
        long n = 0;
        try(Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sqlCode);
            while(rs.next()){
                n = rs.getLong(1);
            }
            
	}
	catch(SQLException e){}
        
        return n;
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    public String[] login(Connection con, String sqlCode, String email) throws SQLException{
        String[] res = new String[2];
        res[0] = "";
        res[1] = "";
        
        try (PreparedStatement stmt = con.prepareStatement(sqlCode)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if(rs == null){
                return res;
            }
            while (rs.next()) {
                int numCol = rs.getMetaData().getColumnCount();
                for(int i = 1; i <= numCol; i++){
                    res[i-1] = rs.getString(i);
                }
            }
            rs.close();
        }

        return res;
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    public boolean isProfileActivated(Connection con, String sqlCode, String email) throws SQLException{
        boolean res = false;
        
        try (PreparedStatement stmt = con.prepareStatement(sqlCode)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if(rs == null){
                return res;
            }
            while (rs.next()) {
                int numCol = rs.getMetaData().getColumnCount();
                for(int i = 1; i <= numCol; i++){
                    res = rs.getBoolean(i);
                }
            }
            rs.close();
        }

        return res;
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    public String getActivationCode(Connection con, String sqlCode, String email) throws SQLException{
        String res = "";
        
        try (PreparedStatement stmt = con.prepareStatement(sqlCode)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if(rs == null){
                return res;
            }
            while (rs.next()) {
                int numCol = rs.getMetaData().getColumnCount();
                for(int i = 1; i <= numCol; i++){
                    res = rs.getString(i);
                }
            }
            rs.close();
        }

        return res;
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    public String getEmail(Connection con, String sqlCode, String s) throws SQLException{
        String res = "";
        
        try (PreparedStatement stmt = con.prepareStatement(sqlCode)) {
            stmt.setString(1, s);
            ResultSet rs = stmt.executeQuery();
            if(rs == null){
                return res;
            }
            while (rs.next()) {
                int numCol = rs.getMetaData().getColumnCount();
                for(int i = 1; i <= numCol; i++){
                    res = rs.getString(i);
                }
            }
            rs.close();
        }

        return res;
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    public String[] getUserData(Connection con, String sqlCode, String number) throws SQLException{
        String[] res = new String[4];
        res[0] = "";
        res[1] = "";
        res[2] = "";
        res[3] = "";
        
        try (PreparedStatement stmt = con.prepareStatement(sqlCode)) {
            stmt.setString(1, number);
            ResultSet rs = stmt.executeQuery();
            if(rs == null){
                return res;
            }
            while (rs.next()) {
                int numCol = rs.getMetaData().getColumnCount();
                for(int i = 1; i <= numCol; i++){
                    res[i-1] = rs.getString(i);
                }
            }
            rs.close();
        }

        return res;
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    public String[] getStudentData(Connection con, String sqlCode, String number) throws SQLException{
        String[] res = new String[5];
        res[0] = "";
        res[1] = "";
        res[2] = "";
        res[3] = "";
        res[4] = "";
        
        try (PreparedStatement stmt = con.prepareStatement(sqlCode)) {
            stmt.setString(1, number);
            ResultSet rs = stmt.executeQuery();
            if(rs == null){
                return res;
            }
            while (rs.next()) {
                int numCol = rs.getMetaData().getColumnCount();
                for(int i = 1; i <= numCol; i++){
                    res[i-1] = rs.getString(i);
                }
            }
            rs.close();
        }

        return res;
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    public ArrayList<String> getStudentsNumbers(Connection con, String sqlCode) throws SQLException{
        ArrayList<String> l = new ArrayList();
     
        try(Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sqlCode);
            while(rs.next()){
                l.add(rs.getString(1));
            }
            
	}
	catch(SQLException e){}
        
        return l;
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    public ArrayList<String> getUsersNumbers(Connection con, String sqlCode, String category) throws SQLException{
        ArrayList<String> l = new ArrayList();
        
        String s;
        
        try (PreparedStatement stmt = con.prepareStatement(sqlCode)) {
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();
            if(rs == null){
                return l;
            }
            while (rs.next()) {
                int numCol = rs.getMetaData().getColumnCount();
                for(int i = 1; i <= numCol; i++){
                    l.add(rs.getString(i));
                }
            }
            rs.close();
        }

        return l;
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    public ArrayList<String> getCoursesId(Connection con, String sqlCode) throws SQLException{
        ArrayList<String> l = new ArrayList();
     
        try(Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sqlCode);
            while(rs.next()){
                l.add(rs.getString(1));
            }
            
	}
	catch(SQLException e){}
        
        return l;
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    public boolean isStudyPlanAlreadyRegistered(Connection con, String sqlCode, String number) throws SQLException{
        long n = 0;
        
        try (PreparedStatement stmt = con.prepareStatement(sqlCode)) {
            stmt.setString(1, number);
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                n = rs.getLong(1);
            }
            rs.close();
        }
        
        if(n == 1)
            return true;
        else
            return false;
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    public String getNumber(Connection con, String sqlCode, String email) throws SQLException{
        String res = "";
        
        try (PreparedStatement stmt = con.prepareStatement(sqlCode)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if(rs == null){
                return res;
            }
            while (rs.next()) {
                int numCol = rs.getMetaData().getColumnCount();
                for(int i = 1; i <= numCol; i++){
                    res = rs.getString(i);
                }
            }
            rs.close();
        }

        return res;
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    public ArrayList<String> getStudentStudyPlanCoursesId(Connection con, String sqlCode, String number) throws SQLException{
        ArrayList<String> l = new ArrayList();
        
        String s;
        
        try (PreparedStatement stmt = con.prepareStatement(sqlCode)) {
            stmt.setString(1, number);
            ResultSet rs = stmt.executeQuery();
            if(rs == null){
                return l;
            }
            while (rs.next()) {
                int numCol = rs.getMetaData().getColumnCount();
                for(int i = 1; i <= numCol; i++){
                    l.add(rs.getString(i));
                }
            }
            rs.close();
        }

        return l;
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    public String[] getCourseData(Connection con, String sqlCode, String courseId) throws SQLException{
    String[] res = new String[2];
        res[0] = "";
        res[1] = "";
        
        try (PreparedStatement stmt = con.prepareStatement(sqlCode)) {
            stmt.setString(1, courseId);
            ResultSet rs = stmt.executeQuery();
            if(rs == null){
                return res;
            }
            while (rs.next()) {
                int numCol = rs.getMetaData().getColumnCount();
                for(int i = 1; i <= numCol; i++){
                    res[i-1] = rs.getString(i);
                }
            }
            rs.close();
        }

        return res;
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    public ArrayList<String> getProfessorsId(Connection con, String sqlCode, String courseId) throws SQLException{
        ArrayList<String> l = new ArrayList();
        
        String s;
        
        try (PreparedStatement stmt = con.prepareStatement(sqlCode)) {
            stmt.setString(1, courseId);
            ResultSet rs = stmt.executeQuery();
            if(rs == null){
                return l;
            }
            while (rs.next()) {
                int numCol = rs.getMetaData().getColumnCount();
                for(int i = 1; i <= numCol; i++){
                    l.add(rs.getString(i));
                }
            }
            rs.close();
        }

        return l;
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    public int isUserSigned(Connection con, String sqlCode, String number, String courseId) throws SQLException{
        int res = 0;
        
        try (PreparedStatement stmt = con.prepareStatement(sqlCode)) {
            stmt.setString(1, number);
            stmt.setString(2, courseId);
            ResultSet rs = stmt.executeQuery();
            if(rs == null){
                return res;
            }
            while (rs.next()) {
                int numCol = rs.getMetaData().getColumnCount();
                for(int i = 1; i <= numCol; i++){
                    res = rs.getInt(i);
                }
            }
            rs.close();
        }
        return res;
    } 
    
    //--------------------------------------------------------------------------------------------------------
    
    public ArrayList<String> getProfessorHandledCoursesId(Connection con, String sqlCode, String number) throws SQLException{
        ArrayList<String> l = new ArrayList();
        
        String s;
        
        try (PreparedStatement stmt = con.prepareStatement(sqlCode)) {
            stmt.setString(1, number);
            ResultSet rs = stmt.executeQuery();
            if(rs == null){
                return l;
            }
            while (rs.next()) {
                int numCol = rs.getMetaData().getColumnCount();
                for(int i = 1; i <= numCol; i++){
                    l.add(rs.getString(i));
                }
            }
            rs.close();
        }

        return l;
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    public ArrayList<String> getFoldersName(Connection con, String sqlCode, String sectionId) throws SQLException{
        ArrayList<String> l = new ArrayList();
        
        String s;
        
        try (PreparedStatement stmt = con.prepareStatement(sqlCode)) {
            stmt.setString(1, sectionId);
            ResultSet rs = stmt.executeQuery();
            if(rs == null){
                return l;
            }
            while (rs.next()) {
                int numCol = rs.getMetaData().getColumnCount();
                for(int i = 1; i <= numCol; i++){
                    l.add(rs.getString(i));
                }
            }
            rs.close();
        }

        return l;
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    public ArrayList<String> getSectionsId(Connection con, String sqlCode, String courseId) throws SQLException{
        ArrayList<String> l = new ArrayList();
        
        String s;
        
        try (PreparedStatement stmt = con.prepareStatement(sqlCode)) {
            stmt.setString(1, courseId);
            ResultSet rs = stmt.executeQuery();
            if(rs == null){
                return l;
            }
            while (rs.next()) {
                int numCol = rs.getMetaData().getColumnCount();
                for(int i = 1; i <= numCol; i++){
                    l.add(rs.getString(i));
                }
            }
            rs.close();
        }

        return l;
    }
    
    
    //--------------------------------------------------------------------------------------------------------
    
    public ArrayList<String> getSectionName(Connection con, String sqlCode, String sectionId) throws SQLException{
        ArrayList<String> l = new ArrayList();
        
        String s;
        
        try (PreparedStatement stmt = con.prepareStatement(sqlCode)) {
            stmt.setString(1, sectionId);
            ResultSet rs = stmt.executeQuery();
            if(rs == null){
                return l;
            }
            while (rs.next()) {
                int numCol = rs.getMetaData().getColumnCount();
                for(int i = 1; i <= numCol; i++){
                    l.add(rs.getString(i));
                }
            }
            rs.close();
        }

        return l;
    }
    
    
    //--------------------------------------------------------------------------------------------------------
    
    public String[] getSectionData(Connection con, String sqlCode, String sectionId) throws SQLException{
    String[] res = new String[2];
        res[0] = "";
        res[1] = "";
        
        try (PreparedStatement stmt = con.prepareStatement(sqlCode)) {
            stmt.setString(1, sectionId);
            ResultSet rs = stmt.executeQuery();
            if(rs == null){
                return res;
            }
            while (rs.next()) {
                int numCol = rs.getMetaData().getColumnCount();
                for(int i = 1; i <= numCol; i++){
                    res[i-1] = rs.getString(i);
                }
            }
            rs.close();
        }

        return res;
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    public ArrayList<String> getSubsectionData(Connection con, String sqlCode, String upsectionId) throws SQLException{
        ArrayList<String> l = new ArrayList();
        
        String s;
        
        try (PreparedStatement stmt = con.prepareStatement(sqlCode)) {
            stmt.setString(1, upsectionId);
            ResultSet rs = stmt.executeQuery();
            if(rs == null){
                return l;
            }
            while (rs.next()) {
                int numCol = rs.getMetaData().getColumnCount();
                for(int i = 1; i <= numCol; i++){
                    l.add(rs.getString(i));
                }
            }
            rs.close();
        }

        return l;
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    public ArrayList<String> getResourcesData(Connection con, String sqlCode, String sectionId) throws SQLException{
        ArrayList<String> l = new ArrayList();
        
        String s;
        
        try (PreparedStatement stmt = con.prepareStatement(sqlCode)) {
            stmt.setString(1, sectionId);
            ResultSet rs = stmt.executeQuery();
            if(rs == null){
                return l;
            }
            while (rs.next()) {
                int numCol = rs.getMetaData().getColumnCount();
                for(int i = 1; i <= numCol; i++){
                    l.add(rs.getString(i));
                }
            }
            rs.close();
        }

        return l;
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    public long getAuthAttempts(Connection con, String sqlCode, String s) throws SQLException{
        long res = 0;
        
        try (PreparedStatement stmt = con.prepareStatement(sqlCode)) {
            stmt.setString(1, s);
            ResultSet rs = stmt.executeQuery();
            if(rs == null){
                return res;
            }
            while (rs.next()) {
                int numCol = rs.getMetaData().getColumnCount();
                for(int i = 1; i <= numCol; i++){
                    res = rs.getLong(i);
                }
            }
            rs.close();
        }

        return res;
    }
    
    //--------------------------------------------------------------------------------------------------------
    
    public String getSectionVisibility(Connection con, String sqlCode, String sectionId) throws SQLException{
        String res = "";
        
        try (PreparedStatement stmt = con.prepareStatement(sqlCode)) {
            stmt.setString(1, sectionId);
            ResultSet rs = stmt.executeQuery();
            if(rs == null){
                return res;
            }
            while (rs.next()) {
                int numCol = rs.getMetaData().getColumnCount();
                for(int i = 1; i <= numCol; i++){
                    res = rs.getString(i);
                }
            }
            rs.close();
        }

        return res;
    }
    
    //
    
    public ArrayList<String> getUpsectionsId(Connection con, String sqlCode) throws SQLException{
    ArrayList<String> l = new ArrayList();
     
        try(Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery(sqlCode);
            while(rs.next()){
                l.add(rs.getString(1));
            }
            
	}
	catch(SQLException e){}
        
        return l;
    }
}
