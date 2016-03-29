package com.rovicorp.writers;

import java.util.List;

import org.springframework.batch.item.file.FlatFileItemWriter;

public class FlatFileListItemWriter extends FlatFileItemWriter<Object> {
	
	@SuppressWarnings("unchecked")

	@Override
	public void write(List<? extends Object> objects) throws Exception {

		for(Object items:objects){

			super.write((List<? extends Object>) items);

		}
	}

}
