import java.util.Map;
/**
 * Read web server data and analyse hourly access patterns.
 * 
 * @author David J. Barnes and Michael Kölling.
 * @version    2016.02.29
 */
public class LogAnalyzer
{
    // Where to calculate the hourly access counts.
    private int[] hourCounts;
    // Use a LogfileReader to access the data.
    private LogfileReader reader;
    private int[] dailyCounts;
    private int[] monthlyCounts;
    private int[] yearlyCounts;
    

    /**
     * Create an object to analyze hourly web accesses.
     * @param file name of the log file
     */
    public LogAnalyzer(String fileName)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        dailyCounts = new int[29];
        monthlyCounts = new int[13];
        
        // Create the reader to obtain the data.
        reader = new LogfileReader(fileName);
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        reader.reset();
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
        }
    }
    
    /**
     * Analyze the daily data from the log file.
     */
    public void analyzeDailyData()
    {
        reader.reset();
        while(reader.hasNext())
        {
            LogEntry entry = reader.next();
            int day = entry.getDay();
            dailyCounts[day]++;
        }
    }
    
    /**
     * Analyze the monthly data from the log file.
     */
    public void analyzeMonthlyData()
    {
        reader.reset();
        while(reader.hasNext())
        {
            LogEntry entry = reader.next();
            int month = entry.getMonth();
            monthlyCounts[month]++;
        }
    }
    
    /**
     * Print the hourly counts.
     * These should have been set with a prior
     * call to analyzeHourlyData.
     */
    public void printHourlyCounts()
    {
        System.out.println("Hr: Count");
        for(int hour = 0; hour < hourCounts.length; hour++) {
            System.out.println(hour + ": " + hourCounts[hour]);
        }
    }
    
    /**
     * Print the lines of data read by the LogfileReader
     */
    public void printData()
    {
        reader.printData();
    }
    
    /**
     * Return the number of accesses from the log file
     * @return number of accesses from the file
     */
    public int numberOfAccesses()
    {
        int total = 0;
        for(int counts : hourCounts)
        {
            total += counts;
        }
        return total;
    }
    
    /**
     * Return the busiest hour from the log file.
     * @return the busiest hour
     */
    public int busiestHour()
    {
        int busiest = 0;
        int max = 0;
        
        for(int hour = 1; hour < hourCounts.length; hour++)
        {
            if(hourCounts[hour] > max)
            {
                busiest = hour;
                max = hourCounts[hour];
            }
        }
        return busiest;
    }
    
    /**
     * Return the quietest hour from the log file
     * @return the quietest hour
     */
    public int quietestHour()
    {
        int quietest = 0;
        int min = numberOfAccesses();
        
        for(int hour = 0; hour < hourCounts.length; hour++)
        {
            if(hourCounts[hour] < min)
            {
                quietest = hour;
                min = hourCounts[hour];
            }
        }
        return quietest;
    }
    
    /**
     * Return the busiest 2 hour period from the log file.
     * @return the first hour of the busiest 2 hour period
     */
    public int busiestTwoHour()
    {
        int busiest = 0;
        int max = 0;
        
        for(int hour = 0; hour < hourCounts.length; hour++)
        {
            int total = hourCounts[hour] + hourCounts[hour + 1];
            if(total > max)
            {
                busiest = hour;
                max = total;
            }
        }
        return busiest;
    }
    
    /**
     * Return the quietest day from the log file.
     * @return the quietest day
     */
    
    public int quietestDay()
    {
        int quietest = 0;
        int min = numberOfAccesses();
        
        for(int day = 0; day < dailyCounts.length; day++)
        {
            if(dailyCounts[day] < min)
            {
                quietest = day;
                min = dailyCounts[day];
            }
        }
        return quietest;
    }
    
    /**
     * Return the busiest day from the log file
     * @return the busiest day
     */
    public int busiestDay()
    {
        int busiest = 0;
        int max = 0;
        
        for(int day = 0; day < dailyCounts.length; day++)
        {
            if(dailyCounts[day] > busiest)
            {
                busiest = day;
                max = dailyCounts[day];
            }
        }
        return busiest;
    }
    
    /**
     * Return the total number of accesses per month.
     * @return total accesses per month
     */
    public int totalAccessesPerMonth()
    {
        int total = 0;
        for(int month = 0; month < monthlyCounts.length; month++)
        {
            total += monthlyCounts[month];
        }
        return total;
    }
    
    /**
     * Return the quietest month from the log file
     * @return the quietest month
     */
    public int quietestMonth()
    {
        int quietest = numberOfAccesses();
        int min = 0;
        for(int month = 0; month < monthlyCounts.length; month++)
        {
            if(monthlyCounts[month] < quietest)
            {
                quietest = month;
                min = monthlyCounts[month];
            }
        }
        return quietest;
    }
    
    /**
     * Return the busiest month from the log file.
     * @return the busiest month
     */
    public int busiestMonth()
    {
        int busiest = 0;
        int max = 0;
        for(int month = 0; month < monthlyCounts.length; month++)
        {
            if(monthlyCounts[month] > busiest)
            {
                busiest = month;
                max = monthlyCounts[month];
            }
        }
        return busiest;
    }
    
    /**
     * Calculates the average number of accesses per month from the log file.
     * @return average accesses per month
     */
    public int averageAccessesPerMonth()
    {
        int total = 0;
        for(int month : monthlyCounts)
        {
            total += month;
        }
        int average = total / monthlyCounts.length;
        return average;
    }
}
