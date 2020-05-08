package food.taim;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.MyViewHolder> {

    Context context;
    private int count;

    public SliderAdapter(Context context) {
        this.context = context;
    }
    public void setCount(int count){
        this.count=count;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent) {
        View view= LayoutInflater.from(context).inflate(R.layout.srow_slider_view,null);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MyViewHolder viewHolder, int position) {

        switch(position){
            case 0:
                viewHolder.imageView.setImageResource(R.drawable.first);
                break;
            case 1:
                viewHolder.imageView.setImageResource(R.drawable.second);
                break;
            case 2:
                viewHolder.imageView.setImageResource(R.drawable.third);
                break;

        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    public class MyViewHolder extends SliderViewAdapter.ViewHolder{
        View itemView;
        ImageView imageView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.image_view);
            this.itemView=itemView;
        }
    }
}