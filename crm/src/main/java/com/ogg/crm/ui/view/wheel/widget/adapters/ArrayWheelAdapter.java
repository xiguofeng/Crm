package com.ogg.crm.ui.view.wheel.widget.adapters;

import android.content.Context;

import com.ogg.crm.entity.AddressData;


/**
 * The simple Array wheel adapter
 * 
 * @param <T>
 *            the element type
 */
public class ArrayWheelAdapter<T> extends AbstractWheelTextAdapter {

	// items
	private T items[];

	/**
	 * Constructor
	 * 
	 * @param context
	 *            the current context
	 * @param items
	 *            the items
	 */
	public ArrayWheelAdapter(Context context, T items[]) {
		super(context);

		// setEmptyItemResource(TEXT_VIEW_ITEM_RESOURCE);
		this.items = items;
	}

	@Override
	public CharSequence getItemText(int index) {
		if (index >= 0 && index < items.length) {
			T item = items[index];
			if (item instanceof CharSequence) {
				return (CharSequence) item;
			}
			if (item instanceof AddressData) {
				AddressData addressData = (AddressData) item;
				return addressData.getRegionName();
			}
			return item.toString();
		}
		return null;
	}

	@Override
	public int getItemsCount() {
		return items.length;
	}
}
