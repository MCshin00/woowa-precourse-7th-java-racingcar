package racingcar.controller;

import static racingcar.utils.StringParser.parseToInteger;
import static racingcar.utils.StringParser.separate;
import static racingcar.view.InputView.getCarNameInput;
import static racingcar.view.InputView.getRoundCountInput;
import static racingcar.view.OutputView.printRoundResultOutput;
import static racingcar.view.OutputView.printStartMessage;
import static racingcar.view.OutputView.printWinnersOutput;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import racingcar.domain.Car;
import racingcar.domain.Cars;
import racingcar.domain.RoundCount;
import racingcar.domain.Winners;
import racingcar.domain.dto.CarsDto;
import racingcar.utils.RandomGenerator;

public class RacingCarController {
    private final RandomGenerator randomNumberGenerator;

    public RacingCarController(RandomGenerator randomNumberGenerator) {
        this.randomNumberGenerator = randomNumberGenerator;
    }

    public void run() {
        String carNames = getCarNameInput();
        RoundCount roundCount = getTryCount(getRoundCountInput());
        Cars cars = createCarList(carNames);
        printStartMessage();
        playRace(cars, roundCount);
        printWinners(cars);
    }

    private Cars createCarList(String inputNameString) {
        String[] carNames = separate(inputNameString);
        List <Car> carList = Arrays.stream(carNames)
                .map(Car::new)
                .collect(Collectors.toList());
        return new Cars(carList, randomNumberGenerator);
    }

    private RoundCount getTryCount(String inputCountString) {
        int inputCount = parseToInteger(inputCountString);
        return new RoundCount(inputCount);
    }

    private void playRace(Cars cars, RoundCount roundCount) {
        cars.moveAll();
        roundCount.minusCount();
        printRoundResult(cars);
        if (roundCount.isEnd()) {
            return;
        }
        playRace(cars, roundCount);
    }

    private void printRoundResult(Cars cars) {
        CarsDto carsDto = cars.toDto();
        printRoundResultOutput(carsDto);
    }

    private void printWinners(Cars cars) {
        Winners winners = new Winners(cars.getWinners());
        printWinnersOutput(winners.getWinnersList());
    }
}
