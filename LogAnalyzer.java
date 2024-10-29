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

    /**
     * Create an object to analyze hourly web accesses.
     */
    public LogAnalyzer(String fileName)
    { 
        // Create the array object to hold the hourly
        // access counts.
        hourCounts = new int[24];
        // Create the reader to obtain the data.
        reader = new LogfileReader(fileName);
    }

    /**
     * Analyze the hourly access data from the log file.
     */
    public void analyzeHourlyData()
    {
        while(reader.hasNext()) {
            LogEntry entry = reader.next();
            int hour = entry.getHour();
            hourCounts[hour]++;
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
     * number of accesses
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
     * Busiest hour
     */
    public int busiestHour()
    {
        int busiest = 0;
        int max = hourCounts[0];
        
        for(int i = 1; i < hourCounts.length; i++)
        {
            if(hourCounts[i] > max)
            {
                busiest = i;
                max = hourCounts[i];
            }
        }
        return busiest;
    }
    
    /**
     * Quietest hour
     */
    public int quietestHour()
    {
        int quietest = 0;
        int min = 0;
        
        for(int i = 0; i < hourCounts.length; i++)
        {
            if(hourCounts[i] < min)
            {
                quietest = i;
                min = hourCounts[i];
            }
        }
        return quietest;
    }
}
