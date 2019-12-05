package com.example.mygifts.logic


import java.beans.IntrospectionException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.core.util.ExcelBean;
import com.core.util.ExcelUtil;
import com.dao.baseinfo.GiftMapper;
import com.po.Gift;
import com.service.baseinfo.GiftService;

@Transactional
@Service("GiftService")
class GiftService {

	@Autowired
	private GiftMapper GiftMapper;

	@Override
	public List<Gift> findAll() {
		return GiftMapper.findAll();
	}

	@Override
	public int add(Gift t) {
		return GiftMapper.insert(t);
	}

	@Override
	public Gift findById(int id) {
		return GiftMapper.selectByPrimaryKey(id);
	}

	@Override
	public int edit(Gift t) {
		return GiftMapper.updateByPrimaryKeySelective(t);
	}

	@Override
	public int delete(int id) {
		return GiftMapper.delete(id);
	}

	@Override
	public List<Gift> vagueFind(String contant, int currentpage,
			int pagesize) {
		return GiftMapper.vagueFind(contant, currentpage, pagesize);
	}

	public void importExcelInfo(InputStream in, MultipartFile file) throws Exception{
	    List<List<Object>> listobs = ExcelUtil.getBankListByExcel(in,file.getOriginalFilename());
	    \
	    for (int i = 0; i < listobs.size(); i++) {
	    	List<Object> listob = listobs.get(i);
	        Gift vo = new Gift();
	        Gift j= null;
	        try {
				j = GiftMapper.selectByPrimaryKey(Integer.valueOf(String.valueOf(listob.get(0))));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch blistobck
				System.out.println("Test1");
			}
			    vo.setId(Integer.valueOf(String.valueOf(listob.get(0))));
	            vo.setGiftname(String.valueOf(listob.get(1)));
	            vo.setGiftkind(String.valueOf(listob.get(2)));
	            vo.setGiftbudget(Integer.valueOf(String.valueOf(listob.get(3))));
	            vo.setGiftpri(Integer.valueOf(String.valueOf(listob.get(4))));
	            vo.setGifttempcon(String.valueOf(listob.get(5)));
	            vo.setIsvalid(Integer.valueOf(String.valueOf(listob.get(6))));
			if(j == null)
			{
		            GiftMapper.insert(vo);
			}
			else
			{
		            GiftMapper.updateByPrimaryKeySelective(vo);
			}
        }

	}

	public XSSFWorkbook exportExcelInfo() throws InvocationTargetException, ClassNotFoundException, IntrospectionException, ParseException, IllegalAccessException {

	    List<Gift> list = GiftMapper.findAll();

	    List<ExcelBean> excel=new ArrayList<>();
	    Map<Integer,List<ExcelBean>> map=new LinkedHashMap<>();
	    XSSFWorkbook xssfWorkbook=null;

	    excel.add(new ExcelBean("id","id",0));
	    excel.add(new ExcelBean("Giftname","Giftname",0));
	    excel.add(new ExcelBean("Giftkind","Giftkind",0));
	    excel.add(new ExcelBean("Giftbudget","Giftbudget",0));
	    excel.add(new ExcelBean("Giftpri","Giftpri",0));
	    excel.add(new ExcelBean("Gifttempcon","Gifttempcon",0));
	    excel.add(new ExcelBean("isvalid","isvalid",0));
	    map.put(0, excel);
	    String sheetName = "Date";

	    xssfWorkbook = ExcelUtil.createExcelFile(Gift.class, list, map, sheetName);
	    return xssfWorkbook;
	}



}