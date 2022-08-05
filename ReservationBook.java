package day1test;

import java.sql.SQLException;
import java.util.ArrayList;

public class ReservationBook {
	
	static StudentVO student;
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
	
	// 도서 예약 실행
	public boolean reservationConfirm() {
		boolean flag = false;
		ArrayList<ReservationVO> list = new ArrayList<ReservationVO>();
		ReservationVO vo = new ReservationVO(1, now.getNow(), student.getStd_no(), book.getBook_no() );
		list.add(vo) ;
		try {
			dao.insertReserveBook(list);
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
