package Day1;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationBook {
	
	StudentVO student;
	BookVO book;
	static LibraryDAO dao = new LibraryDAO();
	static DateTimeService now = new DateTimeService(); 
	
	public ReservationBook(int stdNo, int bookNo) throws SQLException {
		student = dao.selectStudent(stdNo);
		book = dao.selectBook(bookNo);
	}
	
	// 학생의 예약중인 도서 권 수 확인 (최대 3권)
	public boolean checkReservationableStu() {
		boolean flag = false;
		if (student.getRsrv_cnt() < 3) {
			flag = true;
		}
		return flag;
	}
	
	// 해당 도서의 예약 인원 확인 (최대 2명)
	public boolean checkReservationableBook() {
		boolean flag = false;
		if (book.getRsrv_people() < 2) {
			flag = true;
		}
		return flag;
	}
	
	// 학생이 해당 책을 대출 또는 예약중인지 확인
	public boolean checkLoanOrRsrv() throws SQLException {
		boolean flag1 = false;
		boolean flag2 = false;
		ArrayList<LoanVO> loanList = new ArrayList<LoanVO>();
		loanList = dao.selectRentalBook(student.getStd_no());
		for (LoanVO vo : loanList) {
			if (vo.getReturn_yn().equals("N")){
				if (vo.getBook_no() == book.getBook_no()) {
					flag1 = true;
					break;
				}
			}
		}
		ArrayList<ReservationVO> rsrvList = new ArrayList<ReservationVO>();
		rsrvList = dao.selectReserveBook(student.getStd_no());
		for (ReservationVO vo : rsrvList) {
			if (vo.getRsrv_yn().equals("Y")){
				if (vo.getStd_no() == student.getStd_no()) {
					flag2 = true;
					break;
				}
			}
		}
		
		
		return (flag1 && flag2);
	}
	
	// 도서 예약 실행
	public boolean reservationConfirm() {
		boolean flag = false;
		ArrayList<ReservationVO> list = new ArrayList<ReservationVO>();
		ReservationVO vo = new ReservationVO(1, now.getNow(), student.getStd_no(), book.getBook_no() );
		list.add(vo) ;
		int bookRsrvCnt = book.getRsrv_people()+1;
		int stdRsrvCnt = student.getRsrv_cnt()+1;
		try {
			// 예약장부 입력
			dao.insertReserveBook(list);
			// 도서에 예약인원 추가
			dao.updateRentalBook(bookRsrvCnt, book.getBook_no());	
			// 학생에 예약권수 추가
			dao.updateStudent(stdRsrvCnt, student.getStd_no());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	// 도서 예약 취소
	public void reservationCancell() {
		
	}
}
