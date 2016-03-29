package com.rovicorp.processors;

import java.util.List;

import org.springframework.batch.item.ItemProcessor;

public interface ListItemProcessor<T> extends ItemProcessor<T, List<T>> {

	@Override
	public List<T> process(T items) throws Exception;
	
}
