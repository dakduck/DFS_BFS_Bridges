package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
private static int[] marked;
private static HashMap<Integer, List<Integer>> g;
private static Queue<Integer> queue;
private static int timer;
private static int[] fup, tin;
    public static void main(String[] args) throws FileNotFoundException {
        File f = new File("input");
	    Scanner in = new Scanner(f);
	    // *** вершины ***
        int p = in.nextInt();
        // *** ребра ***
        int q = in.nextInt();
        marked = new int[p+1];
        int a, b;
        List<Integer> neib;
        g = new HashMap<>();
        while (in.hasNextInt()) {
            a= in.nextInt();
            if (g.containsKey(a)) {
                neib = g.get(a);
            } else {
                neib = new ArrayList<>();
            }
            b= in.nextInt();
            neib.add(b);
            g.put(a, neib);
            if (g.containsKey(b)) {
                neib = g.get(b);
            } else {
                neib = new ArrayList<>();
            }
            neib.add(a);
            g.put(b, neib);
        }
        System.out.println("Список смежности: ");
        for (int i = 1; i <= p; i++) {
            System.out.print(i + ": ");
                System.out.print(g.get(i) + " ");
            System.out.println();
        }
        // *** Обход в глубину ***
        System.out.print("DFS for 1: ");
       // dfs(1);
        // поиск моста
        fup = new int[p+1];
        tin = new int[p+1];
        findB(p);
        /*for (int i = 1; i < marked.length; i++) {
            System.out.print(marked[i] + " ");
        }*/

        marked = new int[p+1];
        queue = new LinkedList<>();
        // *** Обход в ширину ***
        System.out.print("\nBFS for 1: ");
        bfs(1);
    }

    public static void dfs(int v) {
        marked[v] = 1;
        System.out.print(v + " ");
        for (int to :
                g.get(v)) {
            if (marked[to] == 0) {
                dfs(to);
            }
        }
    }
    public static void bridge(int v, int p) {
        marked[v] = 1;
        tin[v] = fup[v] = timer;
        timer++;
        System.out.print(v + " ");
        for (int to :
                g.get(v)) {
            if (to == p) continue;
            if (marked[to] == 0) {
                bridge(to, v);
                //fup[v] = Math.min(tin[p], fup[to]);
                fup[v] = Math.min(fup[v], fup[to]);
                if (fup[to] > tin[v]) {
                    System.out.print("\n" + v + " --- " + to);
                }
            } else {
                //fup[v] = Math.min(fup[v], tin[v]);
                fup[v] = Math.min(fup[v], tin[to]);
            }
        }
    }

    public static void bridge(int v) {
       bridge(v, 0);
    }

    public static void findB(int n) {
        timer = 0;
        n++;
        for (int i=1; i<n; ++i)
            if (marked[i] == 0)
                bridge (i);
    }


    public static void bfs(int v) {
        queue.add(v);
        marked[v] = 1;
        while (queue.size() > 0) {
            v = queue.peek();
            queue.remove();
            System.out.print(v + " ");
            for (int to :
                    g.get(v)) {
                if (marked[to] == 0) {
                    queue.add(to);
                    marked[to] =1;
                }
            }
        }
    }
}
