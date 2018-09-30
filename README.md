# delivery-list
/*

This repository can be used to study the following:

An Example of Android Architecture Components using Java.

Paging library using Database, network and PagedList.

An Example of PagedList.BoundaryCallback

Data Binding Library example

*/

In this example we are caching data within room database. If database is out of queried data PagedList.BoundaryCallback will trigger and we do network call. The network call will save data in database and PagedList is submitted to RecyclerView using DiffUtil.
Pull to refresh will clear the database and will fetch fresh data.

When user clicks on list item, its geo location is shown on map screen along with item description. 
DeliveriesActivity.java is an example of Data Binding Library whereas DeliveryMapActivity.java uses traditional method.




