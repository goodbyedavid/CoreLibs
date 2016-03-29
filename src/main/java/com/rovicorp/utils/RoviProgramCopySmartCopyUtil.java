package com.rovicorp.utils;

public class RoviProgramCopySmartCopyUtil {
	
	public static String smartCopy(String categoryId, int length, String copy, String copyCS, String copyPromo, String copyCSPromo, String copyShort, String copyCSShort, String copyNoCast, String copyCSNoCast, String copyGrid, String copyCSGrid) {
		
		if(categoryId != null && categoryId.length() > 0 && categoryId.equals("1")) {
			String s = copy;
			copy = copyPromo;
			copyPromo = s;
			
			s = copyCS;
			copyCS = copyCSPromo;
			copyCSPromo = s;
		}
		
		String programCopy = "";
		
		programCopy = doSize(length, copyCS, copy); if (!(programCopy == null || programCopy.equals(""))) return programCopy;
		programCopy = doSize(length, copyCSPromo, copyPromo); if (!(programCopy == null || programCopy.equals(""))) return programCopy;
		programCopy = doSize(length, copyCSShort, copyShort); if (!(programCopy == null || programCopy.equals(""))) return programCopy;
		programCopy = doSize(length, copyCSNoCast, copyNoCast); if (!(programCopy == null || programCopy.equals(""))) return programCopy;
		programCopy = doSize(length, copyCSGrid, copyGrid); if (!(programCopy == null || programCopy.equals(""))) return programCopy;
        
        return "";
		
	}
	
	public static String doSize(int length, String cs, String text) {
		int retval=-1;
        if (text == null || text.equals("")) return text;
        if (length > text.length()) return cfnSmartPunctuate(text);
        length--; 
        retval = cutPosition(length, cs); if (retval == -1) return null;
        if (retval >= text.length()) retval = text.length();
        return cfnSmartPunctuate(text.substring(0, retval));
	}
	
	public static int cutPosition(int len, String sentinellist){
        if(sentinellist == null || sentinellist.trim().equals("")) return -1;
        
        int retval = -1;
        String[] sentinels = sentinellist.split(",");
        
        for(int t=0;t<sentinels.length;t++){
            int cs = -1;
            try{
            	cs = Integer.parseInt(sentinels[t].trim());
            } catch(NumberFormatException e) {
            	
            }
            if(cs>len) break;
            retval = cs;
        }
        return retval;
    }
	
	public static String cfnSmartPunctuate(String s)
    {
        if (s == null || s.equals("")) return s;
        String val = s.trim();
        int pos=val.length();//character to replace
        if (pos == 0) return "";
        char testchar = val.charAt(pos - 1); //original value of that 
        boolean appendPeriodBefore = false;
        if(testchar == '\"' || testchar == '\u201D' || testchar == '\'') {
            pos--;
            if(pos != 0) {
            	testchar=val.charAt(pos-1);
            	appendPeriodBefore = true;
            }
        }
        if(testchar=='.' || testchar=='?' || testchar=='!') return s.trim(); //we're good; already punctuated correctly
        if(testchar==';' || testchar==',' || testchar==':') { //need to change the existing punctuaion
            //String rv = val.substring(0, pos) + "." + val.substring(pos, val.length());
            //try { rv += val.substring(pos, val.length() - pos); } catch(Exception e) { }//we may be trying to append beyond the end; so ignore the resulting error
            return val.substring(0, pos-1) + "." + val.substring(pos, val.length());
        }
        //we are not replacing puctuation, we are inserting it...
        //String retval=val +".";
        //try{retval+=val.substring(pos,val.length()-pos);}catch(Exception e) {}//we may be trying to append beyond the end; so ignore the resulting error
        if(appendPeriodBefore)
        	return val.substring(0, pos) + "." + val.substring(pos, val.length());
        else
        	return val + ".";
    }

}