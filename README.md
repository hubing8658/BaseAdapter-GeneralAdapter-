# BaseAdapter-GeneralAdapter-
通用Adapter基类，其他要实现Adapter的只要继承此类GeneralAdapter，只需要实现简单的两个方法，就可省去重写getView等一系列每个Adapter都会用到的方法，节省
直接继承BaseAdapter，重写多个方法的开发时间：
  1、通过实现getItemLayoutId方法，来设置item的布局资源id，可根据不同的item类型来设置不同的布局
  
  2、通过实现convert方法，来绑定item界面的数据
