package com.rovicorp.dto;

import java.util.Comparator;

public class DirectoryFileStats {
	
	private int rowId;
	private String fileName;
	private long fileSize;
	private long generatedTime;
	
	public int getRowId() {
		return rowId;
	}

	public void setRowId(int rowId) {
		this.rowId = rowId;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public long getFileSize() {
		return fileSize;
	}
	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public long getGeneratedTime() {
		return generatedTime;
	}

	public void setGeneratedTime(long generatedTime) {
		this.generatedTime = generatedTime;
	}

	public static Comparator<DirectoryFileStats> fileNameComparator = new Comparator<DirectoryFileStats>() {
		public int compare(DirectoryFileStats ds1, DirectoryFileStats ds2) {
			String fileName1 = ds1.getFileName().toUpperCase();
			String fileName2 = ds2.getFileName().toUpperCase();
			return fileName1.compareTo(fileName2);
		}
	};
	
	public static Comparator<DirectoryFileStats> fileSizeComparator = new Comparator<DirectoryFileStats>() {
		public int compare(DirectoryFileStats ds1, DirectoryFileStats ds2) {
			long fileSize1 = ds1.getFileSize();
			long fileSize2 = ds2.getFileSize();
			return (int) (fileSize1-fileSize2);
		}
	};
	
	public static Comparator<DirectoryFileStats> fileGeneratedTimeComparator = new Comparator<DirectoryFileStats>() {
		public int compare(DirectoryFileStats ds1, DirectoryFileStats ds2) {
			long generatedTime1 = ds1.getGeneratedTime();
			long generatedTime2 = ds2.getGeneratedTime();
			return (int) (generatedTime1-generatedTime2);
		}
	};

}
