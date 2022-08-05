package day1test;

import java.sql.SQLException;
import java.util.ArrayList;

public class BorrowBook {

	StudentVO student;
	BookVO book;
	static LibraryDAO dao = new LibraryDAO();
	static DateTimeService dts = new DateTimeService();

	public BorrowBook(int stdNo, int bookNo) throws SQLException {
		student = dao.selectStudent(stdNo);
		book = dao.selectBook(bookNo);
	}

	// 학생이 대출정지상태인지 확인
	public boolean checkSuspention() {
		boolean flag = false;
		String susDate = student.getStop_date();
		if (susDate.equals("0") ) {
			flag = false;
		} else if ((Integer.parseInt(susDate) - Integer.parseInt(dts.getNow())) > 0){
			flag = true;
		}
		return flag;
	}

	// 학생의 미반납도서 중 연체도서가 있는지
	public boolean checkUnreturnBook() {
		boolean flag = false;
		ArrayList<LoanVO> list = new ArrayList<LoanVO>();
		try {
			list = dao.selectRentalBook(book.getBook_no());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (LoanVO vo : list) {
			if (Integer.parseInt(vo.getExp_return_date()) - Integer.parseInt(dts.getNow()) < 0) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	// 학생이 빌린 도서가 5권 인지? (최대 대여 도서 수량)
	public boolean checkBrrowable() {
		boolean flag = false;
		if (student.getLoan_cnt() < 5) {
			flag = true;
		}
		return flag;
	}

	// 학생이 빌리려는 도서가 도서관에 있는지?
	public boolean checkHaveBook() {
		boolean flag = false;
		if (book.getLoan_yn().toUpperCase().equals("Y")) {
			flag = true;
		}
		return flag;
	}

	// 도서 대여 실행
	public boolean borrowConfirm() {
		boolean flag = false;
		String loanDate = dts.getNow();
		String returnDate = dts.calDate(loanDate, 7);
		ArrayList<LoanVO> list = new ArrayList<LoanVO>();
		LoanVO vo = new LoanVO(1, loanDate, returnDate, student.getStd_no(),book.getBook_no(),null,"N");
		list.add(vo);
		try {
			// 대출장부 입력
			dao.insertRentalBook(list);
			// 도서에 렌탈여부 변경
			dao.updateRentalBook("N", book.getBook_no());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	// 도서 대여 취소
	public void borrowCancell() {

	}

}
