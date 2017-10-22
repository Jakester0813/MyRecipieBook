package com.eleven.group.myrecipiebook.activity;

import android.view.View;
import android.widget.ImageView;

import com.eleven.group.myrecipiebook.R;
import com.squareup.picasso.Picasso;

public class Recipes {
    public static String[] names = new String[]{"Crepes","Pancakes","Scone","French Toast", "Muffins", "Waffles","Toast", "Banana Bread"};
    public static int[] resourceIds = new int[]{R.drawable.crepe, R.drawable.pancake, R.drawable.scone,
            R.drawable.frenchtoast, R.drawable.muffin, R.drawable.waffle, R.drawable.toasted, R.drawable.bananabread};

    public static String[] ingredients = new String[]{
            "1/2 cup all-purpose flour`1 egg`1/4 cup milk`1/4 cup water`1 tbsp melted butter`dash of salt",
            "1 cup all-purpose flour`1 tsp baking powder`dash of salt`1 tbsp sugar`1 egg`1 cup milk`2 tbsp cooking oil",
            "2 cups all-purpose flour`1/3 cup sugar`1 tsp baking powder`1/4 tsp baking soda`dash of salt`1/2 cup unsalted butter, frozen`1/2 cup raisins`1/2 cup sour cream`1 large egg`additional 1 tsp sugar",
            "1 loaf of thick sliced bread`6 large eggs`1/4 cup buttermilk`1/2 tsp vanilla extract`dash of cinnamon",
            "1/2 cup butter`1/2 cup sugar`2 large eggs`3/4 cup applesauce`1 3/4 cup flour`1 tbsp baking powder`dash of salt`1/4 cup melted butter`1/4 tsp cinnamon`additional 1/4 cup sugar",
            "1 1/2 cup all-purpose flour`1/4 cup sugar`1/4 cup cornstarch`2 tsp baking powder`1/2 teaspoon baking soda`dash of salt`1 1/4 cups buttermilk`2 large eggs`1/2 cup unsalted melted butter (cooled)`1 tsp vanilla extract ",
            "1 slice of bread`1 toaster",
            "2 cups all-purpose flour`1 tsp baking soda`dash of salt`1 cup sugar`1/4 cup salted butter`2 large eggs`3 mashed bananas`1 cup plain or vanilla greek yogurt`1 tsp vanilla extract"
    };
    public static String[] directions = new String[]{
            "Whisk together the flour and eggs. Then stir in the milk and water, and add salt and butter; mix thoroughly.`Lightly oil your cooking surface and bring to a medium high heat. Scoop the batter onto the griddle; 1/4 cup per crepe. Tilt the pan so that the batter coats the surface evenly.`Cook the crepe for ~2 minutes, until the bottom is light brown. Loosen with a spatula, flip and cook the other side. Serve hot.",
            "Heat a pan over medium-low heat. In a bowl, mix together the dry ingredients. In another bowl mix together the wet ingredients. Add wet ingredients to dry ingredients; the batter should be lumpy.`Scoop batter onto pan, and flip when you see bubbles.`Flip and cook the other side. Serve hot.",
            "Preheat oven to 400 degrees.`In a bowl, mix flour, 1/3 cup sugar, baking powder, baking soda, and salt. Grate butter into flour mixture and use your fingers to work in butter, mixture should be coarse.`Stir in raisins.`In another bowl, whisk sour cream and egg.`Add sour cream mixture to flour mixture until large dough clumps form. Shape the dough into a ball.`Place on a lightly floured surface and pat into a 7 inch circle roughly 3/4-inch thick. Sprinkle with additional 1 tsp sugar. Cut dough into 8 triangles; place on a cookie sheet lined with parchment paper. Bake until golden brown, about 15 to 17 minutes. Let cool for 5 minutes and serve warm or at room temperature.",
            "Heat pan over medium high heat.`Combine all ingredients in a bowl wide enough to dip bread in.`Dip in one slice of bread in the mixture and flip to coat the other side.`Place bread in pan and flip once browned, usually about a minute.`Repeat for remaining slices.",
            "Preheat oven to 425 and grease muffin pan (12 muffins).`Cream butter and sugar.`Add eggs and applesauce.`In another bowl combine flour, baking powder, and salt.`Add the dry mixture to the wet and stir to combine.`Spoon batter into the muffin pan, filling to the top.`Bake for 15 minutes or until done.`Remove muffins from pan immediately and dip muffin tops in 1/4 cup melted butter. Combine additional 1/4 cup sugar and cinnamon. Sprinkle onto the tops of muffins.",
            "Heat waffle maker.`In a bowl mix together the dry ingredients.`In another bowl mix together the wet ingredients. `Add the wet ingredients to dry ingredients.`Grease waffle maker and pour 1 cup or 1/2 cup of batter into waffle maker, depending on if it is large or small.`Cook under golden brown and serve hot.",
            "Place slice of bread into toaster.`Gently depress the lever on the starboard side of the toaster to initiate the heating mechanism.`After ~3 minutes the bread, now toast, will be forcefully ejected from the top of the toaster. This is normal and not cause for alarm.`Remove the finished toast from the toaster and enjoy with your favorite fruit based spread!",
            "Preheat oven to 350 degrees and grease two loaf pans.`Combine flour, baking soda, and salt.`In another bowl cream the butter and sugar and add the eggs, banana, yogurt, and vanilla.`Add the wet ingredients to the dry ingredients.`Pour batter into the loaf pans.`Bake for 1 hour or until done.`Let cool for 10-15 minutes and then remove from pan. Serve once cooled."
    };
} // Recipes