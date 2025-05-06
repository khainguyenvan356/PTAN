
import java.util.Random;

public class TH1_Multithread {

    static int N = 10;
    static int K = 3;
    static int[] A = new int[N];
    static int[] results = new int[K];

    public static void main(String[] args) throws InterruptedException {
        Random rand = new Random();
        for (int i = 0; i < N; i++) {
            A[i] = rand.nextInt(1000) + 1;
          
        }
            A[N-1]=9;

        Thread[] threads = new Thread[K];
        int blockSize = N / K;

        for (int i = 0; i < K; i++) {
            final int threadId = i;
            final int start = i * blockSize;
            final int end = (i == K - 1) ? N : start + blockSize;

            threads[i] = new Thread(() -> {
                long startTime = System.currentTimeMillis();
				int count = 0;
				for (int j = start; j < end; j++) {
				    if (isDivisibleBy3(A[j])) {
				        count++;
				        results[threadId] = count;
						long endTime = System.currentTimeMillis();
						System.out.printf("T%d: Có  %d chia hết cho 3 - Thời gian = %dms%n",
						        threadId , A[j], endTime - startTime);
				    }
				}
				
            });

            threads[i].start();
        }

        for (int i = 0; i < K; i++) {
            threads[i].join();
        }

        int totalDivisibleBy3 = 0;
        for (int i = 0; i < K; i++) {
            totalDivisibleBy3 += results[i];
        }

        System.out.println("Tổng số chia hết cho 3 trong mảng: " + totalDivisibleBy3);
    }

    public static boolean isDivisibleBy3(int n) {
        return n % 3 == 0;
    }
}
