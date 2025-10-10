package com.foliageh.itmosoalab2.service;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAllowedException;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SessionScoped
public class AsyncTaskService implements Serializable {
    private CompletableFuture<List<Double>> currentTask = null;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    
    @Inject
    private FlatService flatService;

    public boolean launchUniqueLivingSpacesJob() {
        if (currentTask != null && !currentTask.isDone())
            return false;
        
        currentTask = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(10000);
                return flatService.getUniqueLivingSpacesFromDatabase();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Task was interrupted", e);
            }
        }, executorService);
        
        return true;
    }

    public boolean cancelUniqueLivingSpacesJob() {
        if (currentTask == null || currentTask.isDone())
            return false;
        currentTask.cancel(true);
        currentTask = null;
        return true;
    }

    public List<Double> getTaskResult() {
        if (currentTask == null)
            return null;
        if (!currentTask.isDone())
            throw new NotAllowedException("Task is still in progress");
        try {
            return currentTask.get();
        } catch (Exception e) {
            throw new RuntimeException("Error getting task result", e);
        }
    }

    public boolean isTaskInProgress() {
        return currentTask != null && !currentTask.isDone();
    }

    public boolean isTaskCompleted() {
        return currentTask != null && currentTask.isDone();
    }
}
