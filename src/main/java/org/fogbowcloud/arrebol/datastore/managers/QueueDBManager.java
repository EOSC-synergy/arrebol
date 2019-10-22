package org.fogbowcloud.arrebol.datastore.managers;

import org.fogbowcloud.arrebol.datastore.repositories.DefaultQueueRepository;
import org.fogbowcloud.arrebol.queue.DefaultQueue;

public class QueueDBManager {

    private static QueueDBManager instance;

    private DefaultQueueRepository defaultQueueRepository;

    private QueueDBManager() {
    }

    public synchronized static QueueDBManager getInstance() {
        if (instance == null) {
            instance = new QueueDBManager();
        }
        return instance;
    }

    public void save(DefaultQueue queue) {
        this.defaultQueueRepository.save(queue);
    }

    public void setDefaultQueueRepository(DefaultQueueRepository defaultQueueRepository) {
        this.defaultQueueRepository = defaultQueueRepository;
    }
}
