package com.linewell.archive.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.linewell.archive.R;
import com.linewell.core.common.BaseActivity;
import com.linewell.support.recycler.ItemClickSupport;
import com.linewell.support.recycler.ViewHolder;
import com.linewell.support.recycler.section.OnSectionClickListener;
import com.linewell.support.recycler.section.Section;
import com.linewell.support.recycler.section.SectionAdapter;
import com.linewell.support.recycler.section.SectionEx;
import com.linewell.support.recycler.section.SectionHFViewDelegate;
import com.linewell.support.recycler.section.SectionItemViewDelegate;

import java.util.Random;

/**
 * <pre>
 *     author : dell
 *     e-mail : iopaaa@126.com
 *     time   : 2018/10/11
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class TestSectionActivity extends BaseActivity {
    public static final String LOG = "log";

    public static void start(Context context) {
        Intent starter = new Intent(context, TestSectionActivity.class);
        context.startActivity(starter);
    }

    RecyclerView recyclerView;
    SectionAdapter sectionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_section);
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        recyclerView.addItemDecoration(new SpaceDecoration(12));
//        recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener() {
//            @Override
//            public void onItemClick(RecyclerView.ViewHolder vh) {
//                Toast.makeText(MainActivity.this,"pos:"+vh.getAdapterPosition(),Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onItemLongClick(RecyclerView.ViewHolder vh) {
//
//            }
//        });

        sectionAdapter = new SectionAdapter(this);
        sectionAdapter.addHeaderViewDelegate(new SectionHFViewDelegate(sectionAdapter) {
            @Override
            public void convert(final ViewHolder holder, final Section section) {
                holder.setText(R.id.title, "header");
//                holder.setOnClickListener(R.id.title, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (section instanceof SectionEx) {
//                            sectionEx = (SectionEx) section;
//                        }
//                        Toast.makeText(MainActivity.this,"onHeaderClick:"+holder.getAdapterPosition(),Toast.LENGTH_SHORT).show();
//                    }
//                });
            }

            @Override
            public boolean isForViewType(Section section) {
                return true;
            }

            @Override
            public View getItemViewLayout(Context context, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.item_header, parent, false);
            }

        });
        sectionAdapter.addFooterViewDelegate(new SectionHFViewDelegate(sectionAdapter) {
            @Override
            public void convert(final ViewHolder holder, Section section) {
                holder.setText(R.id.title, "footer");
//                holder.setOnClickListener(R.id.title, new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(MainActivity.this,"onFooterClick:"+holder.getAdapterPosition(),Toast.LENGTH_SHORT).show();
//                    }
//                });
            }

            @Override
            public boolean isForViewType(Section section) {
                return true;
            }

            @Override
            public View getItemViewLayout(Context context, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.item_footer, parent, false);
            }
        });
        sectionAdapter.addItemViewDelegate(new SectionItemViewDelegate(sectionAdapter) {
            @Override
            public void convert(final ViewHolder holder, Section section, final int positionInSection) {
                holder.setText(R.id.title, "item:" + positionInSection);
                holder.setVisible(R.id.button, false);
                holder.setOnClickListener(R.id.button, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(TestSectionActivity.this, "button Click", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public boolean isForViewType(Object item, Section section, int positionInSection) {
                return true;
            }


            @Override
            public View getItemViewLayout(Context context, ViewGroup parent) {
                return LayoutInflater.from(context).inflate(R.layout.item_content, parent, false);
            }
        });
        recyclerView.setAdapter(sectionAdapter);
        ItemClickSupport.addTo(recyclerView)
                .setOnItemClickListener(new OnSectionClickListener(sectionAdapter) {

                    @Override
                    public void onItemClicked(RecyclerView recyclerView, View v, Section section, int position) {
                        Toast.makeText(TestSectionActivity.this, "item pos:" + position, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onHeaderClicked(RecyclerView recyclerView, View v, Section section) {
                        Toast.makeText(TestSectionActivity.this, "header click:", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFooterClicked(RecyclerView recyclerView, View v, Section section) {
                        Toast.makeText(TestSectionActivity.this, "footer click", Toast.LENGTH_SHORT).show();
                    }
                });
        SectionEx sectionEx1 = new SectionEx(true, true);
        SectionEx sectionEx2 = new SectionEx(true, true);
        SectionEx sectionEx3 = new SectionEx(true, true);

        Section section1_1 = new Section(true, true) {
            @Override
            public int getItemCount() {
                return 2;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }
        };
        Section section1_2 = new Section(true, true) {
            @Override
            public int getItemCount() {
                return 1;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }
        };
        sectionEx1.add(section1_1);
        sectionEx1.add(section1_2);

        Section section2_1 = new Section(true, true) {
            @Override
            public int getItemCount() {
                return 2;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }
        };
        sectionEx2.add(section2_1);

        SectionEx section3_1 = new SectionEx(true, true);
        SectionEx section3_2 = new SectionEx(true, true);
        Section section3_2_1 = new Section(true, true) {
            @Override
            public int getItemCount() {
                return 2;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }
        };
        section3_2.add(section3_2_1);
        Section section3_1_1 = new Section(true, true) {
            @Override
            public int getItemCount() {
                return 2;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }
        };
        Section section3_1_2 = new Section(true, true) {
            @Override
            public int getItemCount() {
                return 2;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }
        };
        section3_1.add(section3_1_1);
        section3_1.add(section3_1_2);
        sectionEx3.add(section3_1);
        sectionEx3.add(section3_2);
//        sectionAdapter.addSection(sectionEx1, true);
//        sectionAdapter.addSection(sectionEx2, true);
        sectionAdapter.addSection(sectionEx3, true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void remove(View view) {

        Pair<Section, Integer> sectionForPosition = sectionAdapter.getSectionForPosition(0);
        sectionAdapter.removeSection(sectionForPosition.first, true);

    }

    public void add(View view) {
        Random random = new Random();

        boolean header = true;//random.nextBoolean();
        boolean footer = true;//random.nextBoolean();
        int count = random.nextInt(10);
        Section section = createSection(header, footer, count);

        sectionAdapter.addSection(section, true);
    }

    Section createSection(boolean header, boolean footer, final int count) {
        return new Section(header, footer) {
            @Override
            public int getItemCount() {
                return count;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }
        };
    }


}
