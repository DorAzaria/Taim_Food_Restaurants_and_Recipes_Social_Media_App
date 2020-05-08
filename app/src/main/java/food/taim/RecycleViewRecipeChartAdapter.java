package food.taim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecycleViewRecipeChartAdapter extends RecyclerView.Adapter<RecycleViewRecipeChartAdapter.MyViewHolder> {


    Context mContext;
    List<RecipeChart> mData;

    public RecycleViewRecipeChartAdapter(Context mContext, List<RecipeChart> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.item_recipe_chart,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.recipeName.setText(mData.get(position).getRecipeName());
        holder.infoRecipeName.setText(mData.get(position).getInfoRecipeName());
        holder.imageRecipe.setImageResource(mData.get(position).getPhoto());

    }

    @Override
    public int getItemCount() { return mData.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView recipeName;
        private TextView infoRecipeName;
        private ImageView imageRecipe;
        public MyViewHolder(View itemView) {
            super(itemView);

            recipeName = (TextView) itemView.findViewById(R.id.name_recipe_chart);
            infoRecipeName = (TextView) itemView.findViewById(R.id.info_chart_recipe);
            imageRecipe = (ImageView) itemView.findViewById(R.id.img_recipe_chart);
        }
    }

}
