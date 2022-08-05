package day1test;

import java.sql.SQLException;
import java.util.Scanner;

public class BusinessLogic {

	public void borrowBook(int stuNo, int bookNo) throws SQLException {
		BorrowBook bb = new BorrowBook(stuNo, bookNo);
		boolean borrowable = false;

		borrowable = bb.checkSuspention();
		if (borrowable) {
			bb.borrowCancell();
			System.out.println("대출정지된 학생입니다.");
		} else {
			borrowable = bb.checkUnreturnBook();
			if (borrowable) {
				bb.borrowCancell();
				System.out.println("미반납도서 중 연체도서가 있습니다.");
			} else {
				borrowable = bb.checkBrrowable();
				if (!borrowable) {
					bb.borrowCancell();
					System.out.println("도서대여는 5권까지 가능합니다.");
				} else {
					borrowable = bb.checkHaveBook();
					if (borrowable) {
						bb.borrowConfirm();
						System.out.println("도서대여가 완료되었습니다.");
					} else {
						System.out.println("해당 도서는 모두 대여중 입니다. 예약하시겠습니까?  >> Y/N");
						Scanner scan = new Scanner(System.in);
						String choice = scan.nextLine();
						scan.close();
						if (choice.toUpperCase().equals("Y")) {
							Boolean reservationable = false;
							ReservationBook rb = new ReservationBook(stuNo, bookNo);
							reservationable = rb.checkReservationableStu();
							if (!reservationable) {
								rb.reservationCancell();
								System.out.println("도서예약은 3권까지 가능합니다.");
							} else {
								reservationable = rb.checkReservationableBook();
								if (!reservationable) {
									rb.reservationCancell();
									System.out.println("해당 도서의 예약인원은 2명까지 가능합니다.");
								} else {
									reservationable = rb.checkLoanOrRsrv();						
									if (reservationable) {
										rb.reservationCancell();
										System.out.println("학생이 해당 도서를 이미 대여 또는 예약 중 입니다.");
									} else {
										rb.reservationConfirm();
										System.out.println("도서예약이 완료되었습니다.");
									}
								}
							}
						} else if (choice.equals("N")) {
							bb.borrowCancell();
							System.out.println("도서 대여 및 예약이 취소되었습니다.");
						}
					}
				}
			}
		}
	}
	
	public void returnBook(int stdNo, int bookNo) throws SQLException {
		ReturnBook rb = new ReturnBook(stdNo, bookNo);
		boolean unborrowable = rb.checkUnreturnBook();
		boolean returnable = rb.checkHaveBook();
		
		if (!unborrowable) {
			if (returnable) {
				System.out.println("도서반납이 완료되었습니다.");
				rb.returnConfirm();
			}
		} else {
			unborrowable = rb.checkSuspension();
			if (unborrowable) {
				System.out.println("대출정지일자가 갱신되었습니다.");
				rb.updateSuspension();
			} else {
				System.out.println("대출정지일자가 추가되었습니다.");
				rb.newSuspension();
			}
		}

	}
}
