package model;

/**
 *
 * @author Ale
 */
public class PlatformMonitor {
    
    public static long connectedUsersNumber;
    public static long usersVisualizingCoursesNumber;
    
    public PlatformMonitor(){}
    
    public synchronized void increaseConnectedUsersNumber(){
        connectedUsersNumber ++;
    }
    
    public synchronized void decreaseConnectedUsersNumber(){
        connectedUsersNumber --;
    }
    
    public synchronized void increaseUsersVisualizingCoursesNumber(){
        usersVisualizingCoursesNumber++;
    }
    
    public synchronized void decreaseUsersVisualizingCoursesNumber(){
        usersVisualizingCoursesNumber--;
    }
    
    public long getConnectedUsersNumber(){
        return connectedUsersNumber;
    }
    
    public long getUsersVisualizingCoursesContentsNumber(){
        return usersVisualizingCoursesNumber;
    }
}
