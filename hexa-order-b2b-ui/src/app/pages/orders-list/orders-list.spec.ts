import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, ActivatedRoute } from '@angular/router';
import { OrdersList } from './orders-list';
import { ConfirmationService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';

import {
  provideHttpClientTesting,
  HttpTestingController
} from '@angular/common/http/testing';
import { of } from 'rxjs';

describe('OrdersList', () => {
  let component: OrdersList;
  let fixture: ComponentFixture<OrdersList>;
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        OrdersList,
        ConfirmDialogModule
      ],
      providers: [
        provideRouter([]),
        provideHttpClientTesting(),
        ConfirmationService,
        {
          provide: ActivatedRoute,
          useValue: {
            params: of({}),
            snapshot: {
              paramMap: {
                get: () => null
              }
            }
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(OrdersList);
    component = fixture.componentInstance;
    httpMock = TestBed.inject(HttpTestingController);
  });
  
  it('should create', () => {
    fixture.detectChanges();

    const requests = httpMock.match(() => true);
    requests.forEach(req => req.flush([]));

    expect(component).toBeTruthy();
  });

  afterEach(() => {
    httpMock.verify();
  });
});
