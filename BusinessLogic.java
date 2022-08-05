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
								System.out.println("도서예약은 3권까지 가능합니다.");
								rb.reservationCancell();
							} else {
								reservationable = rb.checkReservationableBook();
								if (!reservationable) {
									System.out.println("해당 도서의 예약인원은 2명까지 가능합니다.");
									rb.reservationCancell();
								} else {
									if (reservationable) {
										System.out.println("도서예약이 완료되었습니다.");
										rb.reservationConfirm();
									}
								}
							}
						} else if (choice.equals("N")) {
							System.out.println("도서 대여 및 예약이 취소되었습니다.");
							bb.borrowCancell();
						}
					}
				}
			}
		}
	}
}
