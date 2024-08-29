package com.kob.botrunningsystem.service.impl.utils;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BotPool extends Thread{
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition(); //条件变量：队列为空就阻塞住，有新的任务进来就唤醒线程
    private final Queue<Bot> bots = new LinkedList<>();

    public void addBot(Integer userId,String botCode, String input){
        lock.lock();
        try {
            bots.add(new Bot(userId, botCode, input));
            condition.signalAll();
        }finally {
            lock.unlock();
        }
    }
    private void consume(Bot bot){
        Consumer consumer = new Consumer();
        consumer.startTimeout(2000,bot);
    }
    @Override
    public void run(){
        while(true){
            lock.lock();
            if(bots.isEmpty()) {
                try {
                    condition.await(); //队列为空就阻塞住线程（会自动释放锁）
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    lock.unlock(); //注意有异常要把锁释放掉
                    break;
                }
            }else{
                Bot bot = bots.remove(); //队列不空，就把队头元素取出执行
                lock.unlock();
                consume(bot); //比较耗时，几秒钟，所以要先解锁
            }
        }
    }
}
