package btc.mkamin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

	public static void main(String[] args) {
		List<Buy> buys = loadBuys();
		List<Sell> sells = loadSells();
		Map<Buy, List<Sell>> arrangetBuysSells = arrange(buys, sells);
		System.out.println(calcIncome(arrangetBuysSells));
	}

	private static Function<Map.Entry<Buy, List<Sell>>, Double> calc4SingleEntry = (e) -> {
		double investment = e.getKey().getAmount() * e.getKey().getPrice();
		double income = e.getValue().stream().map(sell -> sell.getPrice() * sell.getAmount())
				.collect(Collectors.summingDouble(d -> d));
		return income - investment;
	};

	private static Double calcIncome(Map<Buy, List<Sell>> arranged) {
		return arranged.entrySet().stream().map(e -> calc4SingleEntry.apply(e)).collect(Collectors.summingDouble(d->d));
	}

	private static Map<Buy, List<Sell>> arrange(List<Buy> buys, List<Sell> sells) {

		List<Sell> l = new LinkedList<Sell>();
		Map<Buy, List<Sell>> hashMap = new HashMap<Buy, List<Sell>>();

		for (Iterator<Buy> buyIT = buys.iterator(); buyIT.hasNext();) {

			Buy b = ((Buy) buyIT.next());
			double tmpSum = 0;
			double amountBuy = b.getAmount();
			sells.removeAll(l);
			String msg = null;
			String msg1 = null;
			l = new LinkedList<Sell>();

			for (int i = 0; i < sells.size(); i++) {
				Sell s = sells.get(i);
				tmpSum += s.getAmount();

				if (tmpSum > amountBuy) {
					double offset = tmpSum - amountBuy;
					double complement = amountBuy - (tmpSum - s.getAmount());
					s.setAmount(complement);
					l.add(s);

					if (!(i == sells.size() - 1)) {
						Sell sell = sells.get(i + 1);
						msg = "NADWYZKA: "+String.format ("%.6f", offset)+" PRZEKAZANA DO " + sell;
						sell.setAmount(sell.getAmount() + offset);
						msg1 = "SPRZEDAZ PO TRANSFERZE NADWYZKI: "+sell;
					}
					tmpSum = offset;
					break;
				} else {
					l.add(s);
				}
			}

			String newLine = System.getProperty("line.separator");
			System.out.println(newLine);
			System.out.println("=====PODSUMOWANIE POZYCJI KUPNA/SPRZEDAZY=======");
			System.out.println(newLine);
			System.out.println("KUPNO: " + b);
			System.out.println("SPRZEDAZE: " + l);
			
			double sum = l.stream().mapToDouble(i -> i.getAmount()).sum();

			hashMap.put(b, l);

			System.out.println("KUPNO SUMA = " + b.getAmount());
			System.out.println("SPRZEDAZE SUMA = " + sum);
			
			System.out.println(msg);
			System.out.println(msg1);
			
			System.out.println(newLine);
			System.out.println("=====KONIEC PODSUMOWANIA POZYCJI KUPNA/SPRZEDAZY======");
			System.out.println(newLine);

		}
		return hashMap;

	}

	private static List<Sell> loadSells() {
		List<Sell> treeSet = new LinkedList<>();
		treeSet.add(new Sell(1, 0.00183127, 26855.48));
		treeSet.add(new Sell(2, 0.05, 59750));
		treeSet.add(new Sell(3, 0.0096133, 59529));
		treeSet.add(new Sell(4, 0.0003867, 59521));
		treeSet.add(new Sell(5, 0.08403362, 59500));
		treeSet.add(new Sell(6, 0.01596638, 59500));
		treeSet.add(new Sell(7, 0.24, 59600));
		treeSet.add(new Sell(8, 0.00057044, 60430));
		treeSet.add(new Sell(9, 0.03309615, 60430));
		treeSet.add(new Sell(10, 0.03309615, 60430));
		treeSet.add(new Sell(11, 0.01040841, 60430));
		treeSet.add(new Sell(12, 0.01651528, 60550));
		treeSet.add(new Sell(13, 0.00100432, 60550));
		treeSet.add(new Sell(14, 0.04893807, 60550));

		// for (Iterator iterator = treeSet.iterator(); iterator.hasNext();) {
		// // System.out.println((Sell) iterator.next());
		// }

		return treeSet;

	}

	private static List<Buy> loadBuys() {
		List<Buy> treeSet = new LinkedList<>();
		treeSet.add(new Buy(1, 0.00238904, 20928.99));
		treeSet.add(new Buy(2, 0.26921195, 20430));
		treeSet.add(new Buy(3, 0.20795238, 20130));
		treeSet.add(new Buy(4, 0.06527815, 20128));
		// for (Iterator iterator = treeSet.iterator(); iterator.hasNext();) {
		// System.out.println((Buy) iterator.next());
		// }
		// 0.00238904 20928.99
		// 0.26921195 20430
		// 0.20795238 20130
		// 0.06527815 20128

		// TODO Auto-generated method stub
		return treeSet;
	}
	
}
