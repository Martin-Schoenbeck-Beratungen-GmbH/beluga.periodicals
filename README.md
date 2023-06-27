# Beluga Periodicals
## Usage
### Create a Periodical
1. Create a product. Make it a service. This will represent the subscription which the customer will pay for.
2. Open the `Periodical` menu entry. Create new periodical with a short key, a long name, and a description if you like. This entry will work as a node to connect the following steps.
3. Create a `Subscription` in the dedicated tab. 
	* **Name**: a fitting identifier. You may want multiple subscriptions for the same effective periodical, differing in e.g. duration, so you would have a subscription named "my subscription (1 year)" and one called "my subscription (5 years)".
	* **Product**: the service created in step 1. Whenever an order including this product is completed, a new subscriber is created.
	* **Quantity Plan**: how many copies per edition the subscriber should receive. We will come back to this.
	* **Paid Editions**: how many editions the subscriber is supposed to receive. We will come back to this.
	* **Frequency** and **Frequency Type**: the duration the subscription is supposed to last.
	* **Renew automatically**: if ticked, this subscription will renew automatically. This can only work if there was a previous order.
4. Repeat for every option you offer.

### Add subscribers
1. Add subscribers by creating orders including the services you configured as subscriptions.
2. Alternatively use the `I_Periodical` import table to import multiple subscribers at once. Note however that these subscribers cannot be renewed automatically.
3. Look at the `Periodical Subscriber` tab to see them be added.

### Remove subscribers
1. To remove a subscriber untick "renew automatically". This will make sure the customer still receives the last editions they paid for.
2. To remove a subscriber immediately untick "active". They will then be skipped.

### Add editions
1. Open your Periodical.
2. In your toolbar press Process -> Create new Edition.
	* **Product**: the product the customer is supposed to receive, e.g. "some magazine 03/23"
	* **Edition**: the edition's number. May be left blank and will then count by itself but if you migrate your subscriptions to iDempiere, this may help starting out correct.
	* **Quantity Plan**: the amount of the specific product all subscribers are supposed to receive. This is multiplied with each subscriber's **quantity plan** value.
	* **Create Orders**: whether or not to create 100% discount orders for all subscribers. Untick this if you want to only create renewal orders.
	* **Description**: is added to every order.
3. The process now goes through all subscribers who a) are active, b) subscribed today or before, c) subscribed until at least today, d) have more than 0 editions paid or have "renew automatically" ticked.
4. For all subscribers who are to receive no more editions but have "renew automatically" ticked the process will create a renewal order - i.e. an order containing the last subscription to this periodical they bought. If none can be found, an error will be logged but the process will not interrupt.
5. If "create orders" was ticked when the process was started, for every subscriber an order will be created containing a single line - the product configured in the process dialog at a 100% discount.
6. In the `Periodical Edition` tab a new edition will be created as an archive.
