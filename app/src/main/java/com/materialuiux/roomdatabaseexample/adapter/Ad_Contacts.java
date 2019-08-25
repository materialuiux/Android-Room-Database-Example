package com.materialuiux.roomdatabaseexample.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.materialuiux.roomdatabaseexample.models.Contacts;
import com.materialuiux.roomdatabaseexample.ContactsDetailsActivity;
import com.materialuiux.roomdatabaseexample.R;

import java.util.List;


public class Ad_Contacts extends RecyclerView.Adapter<Ad_Contacts.ViewHolder> {

    private Context mContext;
    private List<Contacts> contactsList;
    private LayoutInflater mInflater;

    public Ad_Contacts(Context context, List<Contacts> allContacts) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
        this.contactsList = allContacts;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get the data by position
        final Contacts contacts = contactsList.get(position);
        if (contacts != null) {
            if (!contacts.getFirstName().isEmpty()) {
                // get the first letter from the first name and convert it to image
                holder.avatar.setImageBitmap(getAvatar(contacts.getFirstName().charAt(0)));
            }
            holder.name.setText(contacts.getFirstName() + " " + contacts.getLastName());
            holder.phoneNumber.setText(contacts.getPhoneNumber());
            holder.layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mContext, ContactsDetailsActivity.class);
                    intent.putExtra("id", contacts.getUid());
                    mContext.startActivity(intent);
                }
            });
        }

    }



    private Bitmap getAvatar(char charAt) {
        String text = String.valueOf(charAt).toUpperCase();
        Bitmap bitmap = Bitmap.createBitmap(48, 48, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        Paint paintCircle = new Paint();
        Rect bounds = new Rect();
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Rect rect = new Rect(0, 0, 48, 48);
        RectF rectF = new RectF(rect);

        paint.setColor(Color.WHITE);
        paint.setTextSize(33f);
        paint.setShadowLayer(1f, 0f, 1f, Color.GRAY);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
        paint.getTextBounds(text, 0, 1, bounds);

        paintCircle.setColor(mContext.getResources().getColor(R.color.colorPrimary));
        paintCircle.setAntiAlias(true);

        int x = (canvas.getWidth() / 2);
        int y = (int) ((canvas.getHeight() / 2) - ((paint.descent() + paint.ascent()) / 2));
        canvas.drawRoundRect(rectF, 48, 48, paintCircle);
        canvas.drawText(text, x, y, paint);
        return bitmap;

    }


    @Override
    public int getItemCount() {
        return contactsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        // ui
        ImageView avatar;
        TextView name;
        TextView phoneNumber;
        RelativeLayout layout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.Contact_List_Row);
            avatar = itemView.findViewById(R.id.Contact_Avatar);
            name = itemView.findViewById(R.id.Contact_Name);
            phoneNumber = itemView.findViewById(R.id.Contact_Number);

        }

    }
}
