# Adapter
适用ListView和Recyclerview适配器
#### 用法讲一讲
##### 1、普通列表
- ListView

```
private ItemsAdapter mListAdapter;
mListAdapter = ItemsAdapter.create(this).putItemClass(TestItemView.class).build();
```
- Recyclerview

```
private RecyclerAdapter mRecyclerAdapter;
mRecyclerAdapter = RecyclerAdapter.create(this).putItemClass(TestItemView.class).build();
```
- Recyclerview适配器和ListView适配器通用的TestItemView类代码如下：

```
public class TestItemView extends AbsItemView<ItemBusiness, TestBean> {

    @Override
    protected void initView(Activity activity, ViewGroup parent, ItemBusiness itemBusiness, ViewHolderHelper holderHelper) {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_layout;
    }

    @Override
    public void showItem(ViewHolderHelper holderHelper, TestBean itemData) {
        holderHelper.setText(R.id.test_tv, itemData.s);
    }
}
```
> 在initView（……）方法中可以设置View的一些属性，比如宽高、透明度等等；  
设置数据则是在showItem（……）方法中，需要说明的是这两个方法都有一个ViewHolderHelper的参数，这个是我封装的一个工具类。有一些常用的setText或者setAlpha等一些方法，里面用一个Map来管理View，避免了过多的findViewById，同时也不用写那么多View的成员变量。  
getLayoutId（）这个方法不用多讲了，返回布局Id。

**到这里相信大家也能看出来了，我解决Recyclerview和ListView适配器“通用”的解决方案，是让他们持有相同的ItemView。到时候如果需要更换组件的时候，只需要把代码中RecyclerAdapter.create(this)更换为ItemsAdapter.create(this)，或者反向更换即可。怎么样，是不是很简单呢。**
  
  ![image](http://ubq.ubiaoqing.com/ubiaoqing57e8a1326e33d29011.jpg)

**其实让两个组件适配器“通用”本来就是一个伪命题，需要的参数都不一样。既然此路不通，那么不妨换种思路，让他俩的ItemView通用不就解决问题了吗。**

**AbsItemView这个类比较重要，后续的一系列操作都会用到它。**

##### 2、优雅的实现多种Item Type数据绑定

处理多Item Type的情况，是采用`数据`绑定`AbsItemView`的方式。也就是说一种数据类型对应一种`ItemType`。
多item的范例：
```
ItemsAdapter.create(this)
                .putItemClass(TestItemView.class)
                .putItemClass(TestOneItemView.class)
                .build();
```
由于是示例代码，`TestOneItemView`的代码和`TestItemView`代码差不多相同，唯一区别是：
```
public class TestOneItemView extends AbsItemView<ItemBusiness, TestOneBean>
```
> TestOneItem绑定的数据类型是TestOneBean,没错你没有看错，数据和ItemView之间的绑定是通过泛型来进行的。
> 在`putItemClass`这个方法里，通过反射得到泛型的类型，以此类型的hashCode为key，将ItemView的类型存入一个map中。设置数据时根据不同的数据类型生成不同的ItemView。

当然，如果你的自定义的ItemView不想依赖泛型，想设置成强绑定类型的话，也是支持的。方法如下：
`putItemClass(@NonNull Class<? extends D> itemDataClass,
                                        @NonNull Class<? extends AbsItemView<?, D>> itemViewClass)`
                                        
                                        

##### 3、点击事件以及itemView所需要处理的业务逻辑
点击事件默认Adapter是不进行处理的，你可以通过ListView原生的setOnItemClick来进行设置。  
或者也可以直接在AbsItemView内部直接对根View设置点击事件。
- 点击事件和业务逻辑都通过一个类来实现，就是`ItemBusiness`

```
ItemsAdapter.create(this)
                .putItemClass(TestItemView.class)
                .putItemClass(TestOneItemView.class)
                .setItemBusinessFactory(new ItemBusiness.Factory() {
                    @Override
                    public ItemBusiness create(AbsItemView itemView) {
                    	//itemView方法判断数据类型来返回不同的ItemBusiness
                        if (itemView.isMatch(TestOneBean.class)) {
                            return new ItemBusiness(itemView) {
                                @Override
                                public void onItemClick() {
                                    super.onItemClick();
                                    //点击事件的处理
                                }
                            }.setSetItemClick(true);
                        }
                        return null;
                    }
                })
                .build()
```
>需注意的是，ItemBusiness默认是不处理点击事件，需要通过`setItemClick`方法设置为true才能在Business内处理点击事件。

- ItemBusiness内部本身提供了很多方法,方便于一些业务逻辑的处理，譬如点击某个按钮刷新数据，移除数据啊等等。

```
 //刷新列表
    public final void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }

    //刷新当前ItemView，只支持Recyclerview
    public final void notifyItemDataChanged() {
        setItemData(mPosition, mItemData);
    }
    
    //返回总数据size
    public final int getCount() {
        return mAdapter.getItemCount();
    }

    //返回此项数据
    public final D getItemData() {
        return mItemData;
    }

    //返回当前position
    public final int getPosition() {
        return mPosition;
    }
```
- 当然你也可以自定义ItemBusiness，因为在项目开发的过程中，有可能也会需要在ItemView内处理不少的业务逻辑。譬如网络请求，交互动画啊等等。

#### 好了，一些基本的操作就在这里了。本项目设计遵从MVP思想，将业务逻辑（ItemBusiness）和UI逻辑（AbsItemView）分离开来。从而使得项目结构更加清晰，方便迭代以及测试。
