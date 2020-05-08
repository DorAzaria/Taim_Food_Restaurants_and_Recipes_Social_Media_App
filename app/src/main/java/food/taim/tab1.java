package food.taim;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class tab1 extends Fragment {

    View view;

    private RecyclerView myRec;
    private List<RecipeChart> firstRecipe;
    public tab1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        myRec = (RecyclerView) view.findViewById(R.id.daily_recipe_recycler);
        RecycleViewRecipeChartAdapter recycleViewRecipeChartAdapter = new RecycleViewRecipeChartAdapter(getContext(),firstRecipe);
        myRec.setLayoutManager(new LinearLayoutManager(getActivity()));
        myRec.setAdapter(recycleViewRecipeChartAdapter);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firstRecipe = new ArrayList<>();
        firstRecipe.add(new RecipeChart("המבורגר כמו בויטרינה","יותר טעים מהמקורי!",R.drawable.vitrina));
        firstRecipe.add(new RecipeChart("קוסקוס חלומי","מתכון של סבתא רבקה",R.drawable.koskos));
        firstRecipe.add(new RecipeChart("סושי כמו ביפן","פירוט מלא על הסודות של הסושי",R.drawable.sushi));
        firstRecipe.add(new RecipeChart("פנקייקים עם בננה מקורמלת","מתוק למות",R.drawable.pancake));
        firstRecipe.add(new RecipeChart("פלאפל כרובית","להיט חדש ממליץ בחום!",R.drawable.falafel));
        firstRecipe.add(new RecipeChart("מוקפץ בקר עם קארי","ימתכון חדש שלמדתי בתאילנד",R.drawable.noddle));
        firstRecipe.add(new RecipeChart("המבורגר כמו בויטרינה","יותר טעים מהמקורי!",R.drawable.vitrina));
        firstRecipe.add(new RecipeChart("קוסקוס חלומי","מתכון של סבתא רבקה",R.drawable.koskos));
        firstRecipe.add(new RecipeChart("סושי כמו ביפן","פירוט מלא על הסודות של הסושי",R.drawable.sushi));
        firstRecipe.add(new RecipeChart("פנקייקים עם בננה מקורמלת","מתוק למות",R.drawable.pancake));
        firstRecipe.add(new RecipeChart("פלאפל כרובית","להיט חדש ממליץ בחום!",R.drawable.falafel));
        firstRecipe.add(new RecipeChart("מוקפץ בקר עם קארי","ימתכון חדש שלמדתי בתאילנד",R.drawable.noddle));
    }
}
