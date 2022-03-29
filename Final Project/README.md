# Barter
## Motivation
This program allows the user to enter an account name and password, or to create account. Once logged in, the user can add items or remove items from the account, in which they want to sell or trade online.
## How to Run
You will need the four class files, (Item, Account, BarterServer, BarterClient). As well as the data File("BarterData.dat"). You will need to run the BarterServer class first, then the client class. Once running, you can create an account by clicking the create account button. This will log you on, you then can type in item name, description and price and add the item. To remove an item, select the tab of the item you want to remove and click the remove item button. to save the account, press the Log Out button, in the account pane.
![Image of the Account](https://github.com/KDrake80/CSCI-1175/blob/main/Final%20Project/BarterSnap.png)
## Code Snippet
I picked this portion of the code because I was having a few issues with adding/removing items. Then returning the account to the server to save to the file.
```
account.btAdd.setOnAction(e -> {
				String itemName = account.tfName.getText();
				String itemScript = account.tfDescription.getText();
				String price = account.tfPrice.getText();
				double itemPrice = 0;
				itemPrice = Double.parseDouble(price);
				account.acc.addItem(itemName, itemScript, itemPrice);
				account.tabs.getTabs().clear();
				account.setAccount(mainAccount);
				account.tfName.clear();
				account.tfDescription.clear();
				account.tfPrice.clear();
			});
			account.btRemove.setOnAction(e -> {
				mainAccount.removeItem(account.tabs.getSelectionModel().getSelectedIndex());
				account.tabs.getTabs().clear();
				account.setAccount(mainAccount);
			});
			account.btLogOut.setOnAction(e -> {
				try {
					output.writeObject(mainAccount);
					System.exit(2);
					stage.close();
				}
```
## Tests
I have not used the JUnit tests on this portion of the program. I have ran several trial and error test runs on this. I have spent a few hours debugging and this is the working final project.
## Contributors
I was the only one to work on this project.
[My GitHub](https://github.com/KDrake80)