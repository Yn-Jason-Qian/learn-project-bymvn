package effctiveJava.section1;

public class BuilderPattern {

	public static void main(String[] args) {
		NutritionFacts nf = new NutritionFacts.Builder(200, 300).setFat(2).setCalories(3).build();
		System.out.println(nf);
	}
	
}
class NutritionFacts {
	private final int servingSize;
	private final int servings;
	private final int calories;
	private final int fat;
	private final int sodium;
	private final int carbohydrate;
	
	public static class Builder {
		private final int servingSize;
		private final int servings;
		private int calories;
		private int fat;
		private int sodium;
		private int carbohydrate;
		public Builder(int servingSize, int servings) {
			this.servingSize = servingSize;
			this.servings = servings;
		}
		public Builder setCalories(int calories) {
			this.calories = calories;
			return this;
		}
		public Builder setFat(int fat) {
			this.fat = fat;
			return this;
		}
		public Builder setSodium(int sodium) {
			this.sodium = sodium;
			return this;
		}
		public Builder setCarbohydrate(int carbohydrate) {
			this.carbohydrate = carbohydrate;
			return this;
		}
		
		public NutritionFacts build() {
			return new NutritionFacts(this);
		}
	}
	
	private NutritionFacts(Builder builder) {
		servingSize = builder.servingSize;
		servings = builder.servings;
		calories = builder.calories;
		fat = builder.fat;
		sodium = builder.sodium;
		carbohydrate = builder.carbohydrate;
	}

	@Override
	public String toString() {
		return String.format("NutritionFacts [servingSize=%s, servings=%s, calories=%s, fat=%s, sodium=%s, carbohydrate=%s]", servingSize, servings, calories,
				fat, sodium, carbohydrate);
	}
	
}