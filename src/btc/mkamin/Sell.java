package btc.mkamin;

	public class Sell implements Comparable<Sell> {

		@Override
		public String toString() {
			return "Sell [order=" + order + ", price=" + price + ", amount=" + String.format ("%.6f", amount) + "]";
		}

		private int order;
		private double price;
		private double amount;

		public double getPrice() {
			return price;
		}

		public void setPrice(double price) {
			this.price = price;
		}

		public double getAmount() {
			return round(amount,6);
		}

		public void setAmount(double amount) {
			this.amount = amount;
		}

		public int getOrder() {
			return order;
		}

		public void setOrder(int order) {
			this.order = order;
		}
		
		Sell(int order,double  amount, double price){
			this.order=order;
			this.amount=amount;
			this.price=price;
		}

		@Override
		public int compareTo(Sell o) {
			if(order - o.getOrder() ==0){
				return 0;
			}else {
				return order > o.getOrder() ? 1 : -1;
			}
		}
		
		public static double round(double value, int places) {
		    if (places < 0) throw new IllegalArgumentException();

		    long factor = (long) Math.pow(10, places);
		    value = value * factor;
		    long tmp = Math.round(value);
		    return (double) tmp / factor;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			long temp;
			temp = Double.doubleToLongBits(amount);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			result = prime * result + order;
			temp = Double.doubleToLongBits(price);
			result = prime * result + (int) (temp ^ (temp >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Sell other = (Sell) obj;
			if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
				return false;
			if (order != other.order)
				return false;
			if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
				return false;
			return true;
		}

	
}
