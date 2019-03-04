/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package procese;

//import java.util.ArrayList;

/**
 *
 * @author alexm
 */
public class ProblemData {

    private String cacheType;
    private int cacheCapacity;
    private String schedulerType;
    private ProcessStructure[] processes;
    private int numberOfEvents;
    private int numberOfNumbers;
    private int[] numbersToBeProcessed;
    
    public ProblemData(String cacheType, int cacheCapacity, String schedulerType, ProcessStructure[] processes, int numberOfEvents, int numberOfNumbers, int[] numbersToBeProcessed) {
        this.cacheType = cacheType;
        this.cacheCapacity = cacheCapacity;
        this.schedulerType = schedulerType;
        this.processes = processes;
        this.numberOfEvents = numberOfEvents;
        this.numberOfNumbers = numberOfNumbers;
        this.numbersToBeProcessed = numbersToBeProcessed;
    }
    
    /**
     * @return the cacheType
     */
    public String getCacheType() {
        return cacheType;
    }

    /**
     * @return the cacheCapacity
     */
    public int getCacheCapacity() {
        return cacheCapacity;
    }

    /**
     * @return the schedulerType
     */
    public String getSchedulerType() {
        return schedulerType;
    }

    /**
     * @return the processes
     */
    public ProcessStructure[] getProcesses() {
        return processes;
    }
    
    /**
     * @return the numberOfEvents
     */
    public int getNumberOfEvents() {
        return numberOfEvents;
    }

    /**
     * @return the numberOfNumbers
     */
    public int getNumberOfNumbers() {
        return numberOfNumbers;
    }
    
    /**
     * @return the numbersToBeProcessed
     */
    public int[] getNumbersToBeProcessed() {
        return numbersToBeProcessed;
    }    
}
